package tn.esprit.mfb.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class TransactionDTO {

    private Long transactionId;
    private LocalDate transactionDate;
    private Double amount;
    private Long destination;
    private Long sourceRIB;

}
