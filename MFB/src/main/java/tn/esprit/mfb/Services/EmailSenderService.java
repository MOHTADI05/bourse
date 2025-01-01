package tn.esprit.mfb.Services;

import jakarta.activation.DataSource;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service

public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mfb78756@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail Send...");


    }
    public void sendEmailWithImage(String toEmail, String subject, String message, String imagePath) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("mohtadimarmouri@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);

            // Set HTML message
            helper.setText(message, true);

            // Attach image
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                DataSource dataSource = new ByteArrayDataSource(Files.readAllBytes(imageFile.toPath()), "image/jpeg");
                helper.addAttachment(imageFile.getName(), dataSource);
            } else {
                System.out.println("Image file not found: " + imagePath);
            }

            mailSender.send(mimeMessage);
            System.out.println("Mail Sent Successfully.");
        } catch (IOException e) {
            System.out.println("Failed to send email: " + e.getMessage());
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

