package com.adamwandoch.socialprotoapi.elisafariasapi.mailsender.service;

import com.adamwandoch.socialprotoapi.elisafariasapi.mailsender.entity.EmailDetails;

import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

// Annotation
@Service
// Class
// Implementing EmailService interface
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSenderImpl javaMailSenderImpl;
    @Autowired
    private Environment env;

    @Value("${spring.mail.username}")
    private String sender;

    // To send a simple email
    public String sendSimpleMail(EmailDetails details) {

        try {

            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSenderImpl.setUsername(env.getProperty("MAIL_USER_NOREPLY"));
            javaMailSenderImpl.setPassword(env.getProperty("MAIL_PASSWORD_NOREPLY"));
            javaMailSenderImpl.send(mailMessage);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error while Sending Mail";
        }
    }

    // To send an email with attachment
    public String sendMailWithAttachment(EmailDetails details) {
        MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            // be sent
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());

            // Adding the attachment
            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(file.getFilename(), file);

            javaMailSenderImpl.send(mimeMessage);
            return "Mail sent Successfully";
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            return "Error while sending mail!!!";
        }
    }
}