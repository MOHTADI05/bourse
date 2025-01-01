package tn.esprit.mfb.serviceInterface;

public interface InterfaceEmailSender {
    void sendSimpleEmail(String toEmail,
                         String subject,
                         String body
    );
}
