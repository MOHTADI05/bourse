package tn.esprit.mfb.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.serviceInterface.InterfaceEmailSender;

@AllArgsConstructor
@Service
public class EmailService implements InterfaceEmailSender {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mohtadimarmouri@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        try {
            mailSender.send(message);
            System.out.println("Mail Sent Successfully.");
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
/*
    public void sendEmailWithImage(String toEmail, String subject, String message, String imagePath) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("mariemsmaoui12@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            // Set HTML message
            helper.setText(message, true);
            // Attach image
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                DataSource dataSource = new DataSource(Files.readAllBytes(imageFile.toPath()), "image/jpeg");
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
    }*/
}