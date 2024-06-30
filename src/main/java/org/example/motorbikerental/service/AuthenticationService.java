package org.example.motorbikerental.service;


import org.example.motorbikerental.dto.JwtAuthenticationResponse;
import org.example.motorbikerental.dto.RefreshTokenRequest;
import org.example.motorbikerental.dto.SigninRequest;
import org.example.motorbikerental.dto.SignupRequest;
import org.example.motorbikerental.entity.User;

public interface AuthenticationService {

     JwtAuthenticationResponse signin(SigninRequest signinRequest);

     JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

     String checkEmail(String email);

     User signUp(SignupRequest signupRequest);
}
