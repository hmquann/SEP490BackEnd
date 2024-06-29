package org.example.motorbikerental.controller;

import lombok.RequiredArgsConstructor;
import org.example.motorbikerental.entity.User;
import org.example.motorbikerental.service.EmailService;
import org.example.motorbikerental.service.PasswordService;
import org.example.motorbikerental.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;
    private final UserService userService;
    private final EmailService emailService;
    @PostMapping (value="/forgot")
    public ResponseEntity<User> forgotPassword(@RequestParam String email){
        return null;
    }
}
