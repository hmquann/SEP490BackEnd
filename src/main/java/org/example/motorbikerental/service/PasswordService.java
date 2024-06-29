package org.example.motorbikerental.service;


import org.example.motorbikerental.entity.User;

public interface PasswordService {
    void forgotPassword(User user, String password);


}
