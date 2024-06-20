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
    public org.springframework.security.core.userdetails.User updateUser(Long id, org.springframework.security.core.userdetails.User user) {
        return null;
    }

    @Override
    public User updateUser(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!userRepository.existsById(id)) {
            throw new UsernameNotFoundException("User with id " + id + " not found");
        }
        if (optionalUser.isPresent()) {
            User existUser = optionalUser.get();

            existUser.setFirstName(user.getFirstName());
            existUser.setLastName(user.getLastName());
            existUser.setEmail(user.getEmail());
            existUser.getRoles().clear();
            for (Role role : user.getRoles()) {
                // Kiểm tra xem vai trò đã tồn tại chưa
                Role existingRole = roleRepository.findByName(role.getName());
                if (existingRole != null) {
                    existUser.getRoles().add(existingRole);
                } else {
                    // Nếu vai trò chưa tồn tại, thêm vai trò mới vào cơ sở dữ liệu
                    roleRepository.save(role);
                    existUser.getRoles().add(role);
                }
            }
            return userRepository.save(existUser);

        }
        return null;

    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }


}