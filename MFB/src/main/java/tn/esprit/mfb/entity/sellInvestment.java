package tn.esprit.mfb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class sellInvestment {
    @Id
    private Long userId;
    private Long imbId;
    private Long amount;


    public sellInvestment() {

    }

}
