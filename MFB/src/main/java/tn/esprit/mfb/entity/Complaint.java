package tn.esprit.mfb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Complaint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="complaintId")
    Integer complaintId;

    @Temporal (TemporalType.DATE)
    Date complaintDate;
    @Temporal (TemporalType.DATE)
    @Column(name="Response_date")
    Date complaintResponseDate;

    @Enumerated(EnumType.STRING)
    private ComplaintCategories complaintCategory; 

    @Enumerated(EnumType.STRING)
    private ComplaintUrgencies complaintUrgency;

    @Enumerated(EnumType.STRING)
    private ComplaintStatus complaintStatus;


    private String complaintDescription;
    private String complaintResponse;
    private Boolean  sharable;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    String AttachComplaint;




    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "userc_id")
    @JsonIgnore // Add this annotation
    private User userc;


    @OneToMany(mappedBy = "complaint", cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Notification> notifications;




}
