package org.example.motorbikerental.service;

import org.example.motorbikerental.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDetailsService userDetailsService();
    User getUserById(Long id);


    User getUserByEmail(String email);

    User getUserByToken(String token);


    User updateUser(Long id, User user);
    void deleteUser(Long id);
    List<User> getAllUser();
    void toggleUserActiveStatus(Long id);
    void updateUserBalance(Long id, double balance);
    void activeUser(Long id);
    void updateUserBalance(Long id, double balance);
    void activeUserStatus(Long id);

    void updateUserEmail(Long id, String email);


}
