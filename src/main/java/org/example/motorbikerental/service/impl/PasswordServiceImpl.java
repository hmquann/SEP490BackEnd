package org.example.motorbikerental.service.impl;


import lombok.RequiredArgsConstructor;
import org.example.motorbikerental.entity.User;
import org.example.motorbikerental.repository.UserRepository;
import org.example.motorbikerental.service.PasswordService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void forgotPassword(User user, String password) {
        user.setPassword(password);
        userRepository.save(user);
    }


}
