package tn.esprit.mfb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Setter
@Getter
@AllArgsConstructor
public class InvestmentSummary implements Serializable {
  @Id
    private Long invId;
    private Long userId;
    private Long imbId;
    private Long totalAmountInvested;

    public InvestmentSummary() {

    }


    // Getters and setters
}
