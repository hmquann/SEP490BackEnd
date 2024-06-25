package org.example.motorbikerental.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.motorbikerental.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface EmailService {
    String sendVerificationEmail(User user, String url);

    String sendMail(MultipartFile[] file, String to, String[] cc, String subject, String body);

    String getSiteUrl(HttpServletRequest request);

    String sendForgotPasswordEmail(User user,String url);

    String sendChangeEmail(User user, String url,String newEmail);

}