//package gaji.service.jwt.service;
//
//import gaji.service.jwt.entity.RefreshEntity;
//import gaji.service.jwt.filter.JWTUtil;
//import gaji.service.jwt.rpository.RefreshRepository;
//import gaji.service.oauth2.dto.CustomOAuth2User;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Collection;
//import java.util.Date;
//import java.util.Iterator;
//
//@Component
//
//public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//    private final JWTUtil jwtUtil;
//    private final RefreshRepository refreshRepository;
//
//    public CustomSuccessHandler(JWTUtil jwtUtil, RefreshRepository refreshRepository) {
//        this.jwtUtil = jwtUtil;
//        this.refreshRepository = refreshRepository;
//    }
//
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {
//        //OAuth2User
//        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
//
//        String usernameId = customUserDetails.getName();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority auth = iterator.next();
//        String role = auth.getAuthority();
//
//        //토큰 생성
//        String access = jwtUtil.createJwt("access", usernameId, role, 600000L);
//        String refresh = jwtUtil.createJwt("refresh", usernameId, role, 86400000L);
//
//        //DB에 저장되어 있는지 확인
//        Boolean isExist = refreshRepository.existsByUsername(usernameId);
//        if (!isExist) {
//            addRefreshEntity(usernameId, refresh, 86400000L);
//        }
//
//
//        //응답 설정
//        response.setHeader("access", access);
//        response.addCookie(createCookie("refresh", refresh));
//        response.setStatus(HttpStatus.OK.value());
//        response.sendRedirect("http://localhost:8080/my");
//
//    }
//
//    private Cookie createCookie(String key, String value) {
//
//        Cookie cookie = new Cookie(key, value);
//        cookie.setMaxAge(60*60*60);
//        cookie.setSecure(true);
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//
//        return cookie;
//    }
//
//    private void addRefreshEntity(String username, String refresh, Long expiredMs) {
//
//        Date date = new Date(System.currentTimeMillis() + expiredMs);
//
//        RefreshEntity refreshEntity = new RefreshEntity();
//        refreshEntity.setUsername(username);
//        refreshEntity.setRefresh(refresh);
//        refreshEntity.setExpiration(date.toString());
//
//        refreshRepository.save(refreshEntity);
//    }
//}

package gaji.service.jwt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import gaji.service.jwt.entity.RefreshEntity;
import gaji.service.jwt.filter.JWTUtil;
import gaji.service.jwt.rpository.RefreshRepository;
import gaji.service.oauth2.dto.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final ObjectMapper objectMapper;

    public CustomSuccessHandler(JWTUtil jwtUtil, RefreshRepository refreshRepository, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        String usernameId = customUserDetails.getName();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER");

        String access = jwtUtil.createJwt("access", usernameId, role, 600000L);
        String refresh = jwtUtil.createJwt("refresh", usernameId, role, 86400000L);

        if (!refreshRepository.existsByUsername(usernameId)) {
            addRefreshEntity(usernameId, refresh, 86400000L);
        }

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access);
        tokens.put("refresh_token", refresh);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());

        String tokensJson = objectMapper.writeValueAsString(tokens);

        // 프론트엔드 URL에 토큰을 쿼리 파라미터로 추가
        String redirectUrl = "http://localhost:8080/auth-success?tokens=" + tokensJson;

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());
        refreshRepository.save(refreshEntity);
    }
}