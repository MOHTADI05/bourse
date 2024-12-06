package tn.esprit.trading_investissement.Dto;



import java.time.LocalDateTime;

public class ActionDTO {
    private String description;
    private LocalDateTime timestamp;

    // Constructor
    public ActionDTO() {}

    public ActionDTO(String description, LocalDateTime timestamp) {
        this.description = description;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

