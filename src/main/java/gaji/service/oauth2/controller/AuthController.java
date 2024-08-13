package gaji.service.oauth2.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/login/{provider}")
    public ResponseEntity<?> loginRedirect(@PathVariable String provider) {
        String redirectUrl = "/oauth2/authorization/" + provider;
        Map<String, String> response = new HashMap<>();
        response.put("redirect_url", redirectUrl);
        return ResponseEntity.ok(response);
    }
}