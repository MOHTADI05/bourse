package tn.esprit.mfb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String notificationSubject;
    private String notificationContent;
    private Long notificationDestin;
    private boolean notificationSeen = false;
    @Temporal (TemporalType.DATE)
    Date notificationDate;
    @Enumerated(EnumType.STRING)
    private ComplaintCategories complaintCategory;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "complaintId" )
    @JsonIgnore // Add this annotation
    private Complaint complaint;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user")
    @JsonIgnore // Add this annotation
    private User user;

}
