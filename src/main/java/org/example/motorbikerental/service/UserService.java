package org.example.motorbikerental.service;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface UserService {
    UserDetailsService userDetailsService();
     org.example.motorbikerental.entity.User getUserById(Long id);

    org.example.motorbikerental.entity.User getUserByEmail(String email);

    org.example.motorbikerental.entity.User getUserByToken(String token);

    void deleteUser(Long id);

    void updateUserBalance(Long id, BigDecimal balance);

    void withDrawMoney(Long id, BigDecimal amount) throws Exception;





}
