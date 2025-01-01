package tn.esprit.mfb.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDTO {
    private Long notificationId;
    private String notificationSubject;
    private String notificationContent;
    private Long notificationDestinUserId;
    private Long notificationSenderUserID;
    private String notificationSenderUsername;
    private boolean notificationSeen;
}
