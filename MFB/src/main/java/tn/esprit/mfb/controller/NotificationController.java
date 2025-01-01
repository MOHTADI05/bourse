package tn.esprit.mfb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Services.NotificationService;
import tn.esprit.mfb.entity.ComplaintCategories;
import tn.esprit.mfb.entity.Notification;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/create")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification createdNotification = notificationService.createNotification(notification);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long notificationId) {
        Notification notification = notificationService.getNotificationById(notificationId);
        return notification != null ?
                new ResponseEntity<>(notification, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsForUser(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getNotificationsForUser(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/unseen")
    public ResponseEntity<List<Notification>> getUnseenNotificationsForUser(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getUnseenNotificationsForUser(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/complaint/{complaintId}")
    public ResponseEntity<List<Notification>> getNotificationsForComplaint(@PathVariable Long complaintId) {
        List<Notification> notifications = notificationService.getNotificationsForComplaint(complaintId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/categorynotif")
    public ResponseEntity<List<Notification>> getNotificationsByCategory(@RequestParam("category") ComplaintCategories category) {
        List<Notification> notifications = notificationService.getNotificationsByCategory(category);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PutMapping("/seen/{notificationId}")
    public ResponseEntity<String> markNotificationAsSeen(@PathVariable Long notificationId) {
        notificationService.markNotificationAsSeen(notificationId);
        return new ResponseEntity<>("Notification marked as seen", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return new ResponseEntity<>("Notification deleted successfully", HttpStatus.OK);
    }
}
