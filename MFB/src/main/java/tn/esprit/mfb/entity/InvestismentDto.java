package tn.esprit.mfb.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class InvestismentDto {
 @Id
 private Long userId;
 private Long amount;
 private Date invDate;
 private Long imbId;

    public InvestismentDto() {

    }

}
