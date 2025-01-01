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
public class InvestmentRequest implements Serializable {
    @Id

        private Long immobilierId;
        private Long cin;
        private Long amount;

    public InvestmentRequest() {

    }
}
