package org.example.motorbikerental.service.impl;



import jakarta.servlet.http.HttpServletRequest;
import org.example.motorbikerental.entity.User;
import org.example.motorbikerental.service.EmailService;
import org.example.motorbikerental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;


    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    private final UserService userService;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.userService = userService;
    }

    @Override
    public String sendVerificationEmail(User user, String url) {
        return null;
    }

    @Override
    public String sendMail(MultipartFile[] file, String to, String[] cc, String subject, String body) {
        return null;
    }

    @Override
    public String getSiteUrl(HttpServletRequest request) {
        return null;
    }

    @Override
    public String sendForgotPasswordEmail(User user, String url) {
        return null;
    }

    @Override
    public String sendChangeEmail(User user, String url, String newEmail) {
        return null;
    }
}
