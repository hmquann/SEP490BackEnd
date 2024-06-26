package org.example.motorbikerental.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.example.motorbikerental.dto.ChangeEmailRequest;
import org.example.motorbikerental.dto.JwtAuthenticationResponse;
import org.example.motorbikerental.dto.RefreshTokenRequest;
import org.example.motorbikerental.dto.SigninRequest;
import org.example.motorbikerental.entity.User;
import org.example.motorbikerental.service.AuthenticationService;
import org.example.motorbikerental.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final EmailService emailService;
    private final UserService userService;



    @RequestMapping (value="/signup",method =RequestMethod.POST)
    public ResponseEntity<User> signUp(@RequestBody SignupRequest signupRequest, HttpServletRequest httpServletRequest){
        User user = authenticationService.signUp(signupRequest);
        String url = httpServletRequest.getRequestURL().toString()+"/verify/"+user.getToken();
        String newUrl = url.replace("localhost:8080", "localhost:3000");
        emailService.sendVerificationEmail(user, newUrl.replace(httpServletRequest.getServletPath(),""));
        return ResponseEntity.ok(user);

    }

    @CrossOrigin
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest){
            return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }


    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

    @RequestMapping (value="/changeEmail",method =RequestMethod.POST)
    public ResponseEntity<User> changeEmail(@RequestBody ChangeEmailRequest changeEmailRequest, HttpServletRequest httpServletRequest){
        User user = userService.getUserById(changeEmailRequest.getUserId());
        String newEmail = changeEmailRequest.getNewEmail();
        authenticationService.checkEmail(newEmail);
        String url = httpServletRequest.getRequestURL().toString()+"/updateEmail/"+user.getToken()+"/"+newEmail;
        String newUrl = url.replace("localhost:8080", "localhost:3000");
        emailService.sendChangeEmail(user, newUrl.replace(httpServletRequest.getServletPath(),""), newEmail);
        return ResponseEntity.ok(user);

    }


}
