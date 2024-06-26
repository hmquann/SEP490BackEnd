package org.example.motorbikerental.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.motorbikerental.entity.Role;
import org.example.motorbikerental.entity.User;
import org.example.motorbikerental.repository.RoleRepository;
import org.example.motorbikerental.repository.UserRepository;
import org.example.motorbikerental.service.UserService;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByEmailOrPhone(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                if (!user.isActive()) {
                    throw new LockedException("User is locked");
                }
                return user;
            }

        };
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found"));
    }


    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
    }

    @Override
    public User getUserByToken(String token) {
        return userRepository.findByToken(token)
                .orElseThrow(() -> new UsernameNotFoundException("User with token " + token + " not found"));
    }



    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public void updateUserBalance(Long id, BigDecimal balance) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            BigDecimal currentBalance = user.getBalance();
            if (currentBalance != null) {

                BigDecimal newBalance = currentBalance.add(balance);
                user.setBalance(newBalance);
                userRepository.save(user);
            } else {
            }
        } else {
            System.out.println("User with ID " + id + " not found.");
        }
    }

    @Override
    public void withDrawMoney(Long id, BigDecimal amount) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            BigDecimal currentBalance = user.getBalance();
            if(currentBalance.compareTo(amount) < 0) {
                throw new Exception("Insufficient money");
            }
            BigDecimal newBalance = currentBalance.subtract(amount);
            user.setBalance(newBalance);
            userRepository.save(user);

        }

}



}
