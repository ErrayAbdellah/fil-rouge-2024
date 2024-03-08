package com.mobi.mobe.service.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobi.mobe.dto.request.AuthenticationRequest;
import com.mobi.mobe.dto.request.RegisterRequest;
import com.mobi.mobe.dto.response.AuthenticationResponse;
import com.mobi.mobe.entities.Role;
import com.mobi.mobe.entities.Token;
import com.mobi.mobe.entities.User;
import com.mobi.mobe.enums.TokenType;
import com.mobi.mobe.repository.TokenRepository;
import com.mobi.mobe.repository.UserRepository;
import com.mobi.mobe.service.AuthenticationService;
import com.mobi.mobe.service.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepo;
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        Role role = new Role();
        role.setId(1);
        User member = User.builder()
                .firstName(request.getFirstName())
                .lastNAme(request.getLastName())
                .email(request.getEmail())
                .bio(request.getBio())
                .role(role)
                .profilePictureUrl(request.getProfilePictureUrl())
                .gender(request.getGender())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepo.save(member);
        String jwtToken = jwtService.generateToken(member);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepo.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastNAme())
                .role(user.getRole())
                .token(jwtToken).build();
    }
    public void logout() {

        SecurityContextHolder.getContext().setAuthentication(null);
    }



    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepo.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepo.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUserName(refreshToken);
        if (userEmail != null) {
            var user = this.userRepo.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .token(accessToken)
                        .firstName(user.getUsername())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
