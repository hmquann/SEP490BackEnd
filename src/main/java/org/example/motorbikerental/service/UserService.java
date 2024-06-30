package org.example.motorbikerental.service;


import org.example.motorbikerental.entity.User;
import org.springframework.data.domain.Page;
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
    org.example.motorbikerental.entity.User updateUser(Long id, org.example.motorbikerental.entity.User user);
    void updateUserEmail(Long id, String email);

    void activeUser(Long id);
    List<org.example.motorbikerental.entity.User> getAllUser();
    void activeUserStatus(Long id);
    void toggleUserActiveStatus(Long id);
    Page<User> getUserByPagination(int page, int pageSize);


}
