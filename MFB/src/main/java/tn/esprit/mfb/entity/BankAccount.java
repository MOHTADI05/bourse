package tn.esprit.mfb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tn.esprit.mfb.model.TypeAccount;

import java.time.LocalDate;
import java.util.Set;


@Entity
@Getter
@Setter
public class BankAccount {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rib;

    @Column
    private Double balance;

    @Column
    private LocalDate openDate;

    @Column
    private Integer code;

    @Column
    private Integer loyaltyScore;

    @Column
    @Enumerated(EnumType.STRING)
    private TypeAccount typeAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "sourceRIB")
    private Set<Transaction> transactions;

    public void addFunds(double amount) {
        this.balance += amount;
    }



}