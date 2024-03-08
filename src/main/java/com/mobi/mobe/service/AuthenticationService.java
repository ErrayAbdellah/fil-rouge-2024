package com.mobi.mobe.service;

import com.mobi.mobe.dto.request.AuthenticationRequest;
import com.mobi.mobe.dto.request.RegisterRequest;
import com.mobi.mobe.dto.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest user);

    AuthenticationResponse authenticate(AuthenticationRequest user);
    void logout();
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException;
}
