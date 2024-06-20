package org.example.motorbikerental.service;


import org.example.motorbikerental.dto.JwtAuthenticationResponse;
import org.example.motorbikerental.dto.RefreshTokenRequest;
import org.example.motorbikerental.dto.SigninRequest;

public interface AuthenticationService {


     JwtAuthenticationResponse  signin(SigninRequest signinRequest);

     JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
