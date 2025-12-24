package com.commerce.e_commerce.service;

import brevo.ApiClient;
import brevo.Configuration;
import brevo.auth.ApiKeyAuth;
import brevoApi.TransactionalEmailsApi;
import brevoModel.SendSmtpEmail;
import brevoModel.SendSmtpEmailSender;
import brevoModel.SendSmtpEmailTo;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

//    private final JavaMailSender mailSender;
//    private final JavaMailSender javaMailSender;
    @Value("${BREVO_API_KEY}")
    private String brevoApiKey;

//    public void sendVerificationOtpEmail(String userEmail, String otp, String subject, String text) {
//
//        try {
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
//            helper.setSubject(subject);
//            helper.setText(text);
//            helper.setTo(userEmail);
//            javaMailSender.send(mimeMessage);
//        } catch (Exception e) {
//
//            throw new MailSendException("Failed to send email to " + userEmail, e);
//        }
//    }

    public void sendVerificationOtpEmail(String userEmail, String otp) {
        // 1. Configure the Brevo Client
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(brevoApiKey);

        // 2. Create the API Instance
        TransactionalEmailsApi apiInstance = new TransactionalEmailsApi();
        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();

        // 3. Define Sender
        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail("no-reply@your-ecommerce-app.com"); // Can be any fake email
        sender.setName("E-Commerce App");
        sendSmtpEmail.setSender(sender);

        // 4. Define Recipient
        SendSmtpEmailTo recipient = new SendSmtpEmailTo();
        recipient.setEmail(userEmail);
        sendSmtpEmail.setTo(List.of(recipient));

        // 5. Define Content
        sendSmtpEmail.setSubject("Your Login OTP");
        sendSmtpEmail.setHtmlContent("<html><body>" +
                "<h2>Login Verification</h2>" +
                "<p>Your One-Time Password is:</p>" +
                "<h1>" + otp + "</h1>" +
                "<p>This code expires in 5 minutes.</p>" +
                "</body></html>");

        // 6. Send Request (HTTP 443 - Allowed on Render)
        try {
            apiInstance.sendTransacEmail(sendSmtpEmail);
            System.out.println("Brevo email sent successfully to " + userEmail);
        } catch (Exception e) {
            System.err.println("Error sending Brevo email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


