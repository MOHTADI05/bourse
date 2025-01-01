package tn.esprit.mfb.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.NotificationRepository;
import tn.esprit.mfb.Repository.UserRepository;
import tn.esprit.mfb.entity.*;
import tn.esprit.mfb.serviceInterface.INotificationService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Notification createNotification(Notification notification) {
        notification.setNotificationSeen(false);
        return notificationRepository.save(notification);
    }
    @Override
    public void createNotificationp(User user, Optional<Complaint> cl, String subject, String content) {
        if (cl.isPresent()) {
            Notification notification = new Notification();
            notification.setNotificationSubject(subject);
            notification.setNotificationContent(content);
            Complaint complaint = cl.get(); // Retrieve the complaint from the Optional
            notification.setComplaintCategory(complaint.getComplaintCategory()); // Access the complaint category
            notification.setUser(user);

            // Retrieve the user with ADMIN role
            User adminUser = userRepository.findByRole(TypeUser.ADMIN);

            // Check if the adminUser is not null before accessing its properties
            if (adminUser != null) {
                notification.setNotificationDestin(adminUser.getCin());
                notification.setComplaint(complaint);
                notification.setNotificationDate(new Date());
                notificationRepository.save(notification);
            } else {
                // Handle the case where the admin user is not found
                throw new IllegalStateException("Admin user not found");
            }
        } else {
            throw new IllegalArgumentException("Complaint is empty"); // Throw an exception indicating the complaint is empty
        }
    }





    @Override
    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId).orElse(null);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public void markNotificationAsSeen(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setNotificationSeen(true);
            notificationRepository.save(notification);
        });
    }

    @Override
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @Override
    public List<Notification> getNotificationsForUser(Long userId) {
        return notificationRepository.findByUser_cin(userId);
    }

    @Override
    public List<Notification> getNotificationsForComplaint(Long complaintId) {
        return notificationRepository.findByComplaint_ComplaintId(complaintId);
    }

    @Override
    public List<Notification> getUnseenNotificationsForUser(Long userId) {
        return notificationRepository.findByUser_CinAndNotificationSeenFalse(userId);
    }

    @Override
    public List<Notification> getNotificationsByCategory(ComplaintCategories category) {
        return notificationRepository.findByComplaintCategory(category);
    }
}
