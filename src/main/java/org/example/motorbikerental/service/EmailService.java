package org.example.motorbikerental.service;

import org.example.motorbikerental.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpRequest;

public interface EmailService {
    String sendVerificationEmail(User user, String url);


}
