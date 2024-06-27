package org.example.motorbikerental.controller;

import lombok.RequiredArgsConstructor;
import org.example.motorbikerental.entity.User;
import org.example.motorbikerental.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verify")
@RequiredArgsConstructor
public class VerifyController {
    private final UserService userService;
    @GetMapping("/{token}")
    public ResponseEntity<?> verifyToken(@PathVariable String token) {

        User user = userService.getUserByToken(token);
        userService.activeUser(user.getId());
        return ResponseEntity.ok(user);
    }
}
