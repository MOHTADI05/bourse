package tn.esprit.trading_investissement.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private LocalDateTime timestamp;

    @ManyToOne
    @JsonIgnore
    private Room room;

    // Constructors, getters, and setters
    public Action(String description, LocalDateTime timestamp) {
        this.description = description;
        this.timestamp = timestamp;
    }

    public Action() {
    }
}
