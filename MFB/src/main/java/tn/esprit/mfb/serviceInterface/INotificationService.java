package tn.esprit.mfb.serviceInterface;


import tn.esprit.mfb.entity.Complaint;
import tn.esprit.mfb.entity.ComplaintCategories;
import tn.esprit.mfb.entity.Notification;
import tn.esprit.mfb.entity.User;

import java.util.List;
import java.util.Optional;

public interface INotificationService {
    List<Notification> getNotificationsForUser(Long userId);
    List<Notification> getNotificationsForComplaint(Long complaintId);
    List<Notification> getUnseenNotificationsForUser(Long userId);
    List<Notification> getNotificationsByCategory(ComplaintCategories category);




    Notification createNotification(Notification notification);

    void createNotificationp(User user, Optional<Complaint> cl, String subject, String content);

    Notification getNotificationById(Long notificationId);

    //    Notification createNotification(NotificationRequest notificationRequest);
    List<Notification> getAllNotifications();
    void markNotificationAsSeen(Long notificationId);
    void deleteNotification(Long notificationId);

}
