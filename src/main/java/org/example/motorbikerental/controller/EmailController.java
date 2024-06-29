package org.example.motorbikerental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/updateEmail")
@RequiredArgsConstructor
public class EmailController {
    private final UserService userService;
    @PostMapping(value="/{token}/{newEmail}")
    public ResponseEntity<User> updateEmail(@PathVariable String token, @PathVariable String newEmail){
        User user = userService.getUserByToken(token);
        userService.updateUserEmail(user.getId(), newEmail);
        return ResponseEntity.ok(user);
    }

}
