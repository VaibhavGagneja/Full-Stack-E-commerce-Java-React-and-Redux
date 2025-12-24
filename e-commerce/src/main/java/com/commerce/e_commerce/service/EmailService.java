package com.commerce.e_commerce.service;

import brevo.ApiClient;
import brevo.Configuration;
import brevo.auth.ApiKeyAuth;
import brevoApi.TransactionalEmailsApi;
import brevoModel.CreateSmtpEmail;
import brevoModel.SendSmtpEmail;
import brevoModel.SendSmtpEmailSender;
import brevoModel.SendSmtpEmailTo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
        // 1. Configure the Client
        // This sets up the HTTP connection to https://api.brevo.com
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(brevoApiKey);

        // 2. Instantiate the Transactional API
        // This is the specific API for "1-to-1" emails like OTPs
        TransactionalEmailsApi apiInstance = new TransactionalEmailsApi();

        // 3. Create the Email Object
        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();

        // 4. Set Sender
        // CRITICAL: This email must be verified in your Brevo Dashboard > Senders
        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail("vaibhavgagneja@gmail.com"); // Change to your verified sender!
        sender.setName("E-Commerce App");
        sendSmtpEmail.setSender(sender);

        // 5. Set Recipient (From Parameter)
        SendSmtpEmailTo recipient = new SendSmtpEmailTo();
        recipient.setEmail(userEmail);
        sendSmtpEmail.setTo(List.of(recipient));

        // 6. Set Content
        sendSmtpEmail.setSubject("Your Login Verification Code");
        sendSmtpEmail.setHtmlContent("<html><body>" +
                "<h2>Login Verification</h2>" +
                "<p>Your One-Time Password is:</p>" +
                "<h1 style='color:blue;'>" + otp + "</h1>" +
                "<p>This code expires in 10 minutes.</p>" +
                "</body></html>");

        // 7. Send via HTTP API
        try {
            // This line sends a POST request to Brevo's API (Port 443)
            CreateSmtpEmail result = apiInstance.sendTransacEmail(sendSmtpEmail);
            System.out.println("✅ Brevo API Email sent! Message ID: " + result.getMessageId());
        } catch (brevo.ApiException e) {
            System.err.println("❌ Brevo API Error: " + e.getCode());
            System.err.println("❌ Response Body: " + e.getResponseBody());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ General Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}



