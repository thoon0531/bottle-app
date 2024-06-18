package com.bottle_app.event.listener;

import com.bottle_app.event.UserRegistrationEvent;
import com.bottle_app.model.User;
import com.bottle_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class EmailVerificationListener implements ApplicationListener<UserRegistrationEvent> {

    private final JavaMailSender mailSender;
    private final UserService userService;

    @Autowired
    public EmailVerificationListener(JavaMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {
        User user = event.getUser();
        String username = user.getUsername();
        String verificationId = userService.generateVerfication(username);
        String email = user.getEmail();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Bottle App Account Verification");
        message.setText(getText(user, verificationId));
        message.setTo(email);
        mailSender.send(message);
    }

    private String getText(User user, String verificationId) {
        String encodedVerificationId = new String(Base64.getEncoder().encode(verificationId.getBytes()));
        StringBuffer buffer = new StringBuffer();
        buffer.append("Dear ").append(user.getUsername()).append(",")
                .append(System.lineSeparator()).append(System.lineSeparator())
                .append("Your account has been successfully created in Bottle App. ");

        buffer.append("Activate your account by clicking the following link: https://bottle.kasterra.dev/verify/email?id=")
                .append(encodedVerificationId);

        buffer.append(System.lineSeparator());
        buffer.append("Thank you for using Bottle App.");

        buffer.append(System.lineSeparator()).append(System.lineSeparator());
        buffer.append("Regards,").append(System.lineSeparator())
                .append("Bottle App");

        return buffer.toString();
    }
}
