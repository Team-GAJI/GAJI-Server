package gaji.service.oauth2.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final OAuthUserDTO oAuthUserDTO;
    private final boolean isNewUser;


    public CustomOAuth2User(OAuthUserDTO oAuthUserDTO) {

        this.oAuthUserDTO = oAuthUserDTO;
        this.isNewUser = oAuthUserDTO.isNewUser();

    }

    @Override
    public Map<String, Object> getAttributes() {

        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return oAuthUserDTO.getRole().name();
            }
        });

        return collection;
    }

    @Override
    public String getName() {

        return oAuthUserDTO.getUsernameId();
    }

    public boolean isNewUser() {
        return isNewUser;
    }
//    public String getUsernameId() {
//
//        return oAuthUserDTO.getUsernameId();
//    }

}