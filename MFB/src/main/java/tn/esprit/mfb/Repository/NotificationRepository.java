package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mfb.entity.ComplaintCategories;
import tn.esprit.mfb.entity.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByComplaint_ComplaintId(Long complaintId);
    List<Notification> findByUser_CinAndNotificationSeenFalse(Long userId);
    List<Notification> findByComplaintCategory(ComplaintCategories category);

    List<Notification> findByUser_cin(Long userId);
}
