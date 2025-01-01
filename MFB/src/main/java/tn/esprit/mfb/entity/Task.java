package tn.esprit.mfb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int effort;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Temporal (TemporalType.DATE)
    private Date startDate;
    @Temporal (TemporalType.DATE)
    private Date endDate;
    @Temporal (TemporalType.DATE)
    private Date dueDate;

    @ManyToOne
    private User assignedUser;

    private String assignedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private Long tProgress;



    @ManyToOne
    private Complaint complaint;
}