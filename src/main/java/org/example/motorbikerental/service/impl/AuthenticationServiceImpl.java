package org.example.motorbikerental.service.impl;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.motorbikerental.dto.JwtAuthenticationResponse;
import org.example.motorbikerental.dto.RefreshTokenRequest;
import org.example.motorbikerental.dto.SigninRequest;
import org.example.motorbikerental.dto.SignupRequest;
import org.example.motorbikerental.entity.Role;
import org.example.motorbikerental.entity.User;
import org.example.motorbikerental.exception.InactiveUserException;
import org.example.motorbikerental.exception.InvalidCredentialsException;
import org.example.motorbikerental.repository.RoleRepository;
import org.example.motorbikerental.repository.UserRepository;
import org.example.motorbikerental.service.AuthenticationService;
import org.example.motorbikerental.service.JWTService;
import org.hibernate.Hibernate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;
    private final RoleRepository roleRepository;


    @Override
    public User signUp(SignupRequest signupRequest) {
        return null;
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest signinRequest) {
        User user = userRepository.findByEmailOrPhone(signinRequest.getEmailOrPhone()).orElseThrow(
                () -> new InvalidCredentialsException("Invalid email ")
        );
        if (!user.isActive()) {
            throw new InactiveUserException("User is inactive");
        }
        if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Password not correct");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signinRequest.getEmailOrPhone(), signinRequest.getPassword()
            ));
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid email or password");
        }



        Hibernate.initialize(user.getRoles());


        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        Set<Role> roles = user.getRoles();
        List<String> roleNames = roles.stream().map(Role::getName).collect(Collectors.toList());


        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        jwtAuthenticationResponse.setRoles(roleNames);
        jwtAuthenticationResponse.setUser(user);

        jwtAuthenticationResponse.setId(user.getId());
        jwtAuthenticationResponse.setBalance(0.00);
        jwtAuthenticationResponse.setFirstName(user.getFirstName());
        jwtAuthenticationResponse.setLastName(user.getLastName());


        return jwtAuthenticationResponse;
    }


    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmailOrPhone(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthenticationResponse;
        }
        return null;
    }

    @Override
    public String checkEmail(String email) {
        return null;
    }


}
