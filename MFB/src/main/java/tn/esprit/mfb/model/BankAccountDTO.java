package tn.esprit.mfb.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class BankAccountDTO {

    private Long rib;
    private Double balance;
    private LocalDate openDate;
    private Integer Code;
    private Integer loyaltyScore;
    private TypeAccount typeAccount;
    private Long user;

}
