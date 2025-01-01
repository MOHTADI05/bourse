package tn.esprit.mfb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column
    private LocalDate transactionDate;

    @Column
    private Double amount;

    @Column
    private Long destination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sourceribid")
    private BankAccount sourceRIB;

}
