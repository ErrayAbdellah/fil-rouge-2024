package com.mobi.mobe.controller;

import com.mobi.mobe.dto.UserDTO;
import com.mobi.mobe.dto.request.AuthenticationRequest;
import com.mobi.mobe.dto.request.RegisterRequest;
import com.mobi.mobe.dto.response.AuthenticationResponse;
import com.mobi.mobe.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        System.out.println(request);
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authenticationService.logout(); // Implement logout logic in the service
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        authenticationService.refreshToken(request, response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO userOptional = authenticationService.getUserById(userId);
        return ResponseEntity.ok(userOptional);
    }
}
