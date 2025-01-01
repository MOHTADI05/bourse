package tn.esprit.mfb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DemandeCredit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDemandecredit;
    private float amount;
    private Integer minamount;
    private Integer maxamount;
    private String name;
    private LocalDate monthlyPaymentDate;
    private LocalDate demandedate;
    private LocalDate obtainingdate;
    private boolean state;
    private float mounthlypayment;
    private float year;
    private float interest;
    @Enumerated(EnumType.STRING)
    private TypeCredit typeCredit;
    private float Risk;
    private Boolean completed;
    private String Reason;
    private Boolean differe;
    // PERIODE DE DIFFERE
    private float DIFF_period;
    @ManyToOne
    @JsonIgnore
    private User client;

    @OneToOne(mappedBy="demandecredit")
    private Garantor garantor;
    @JsonIgnore
    @ManyToOne
    private Fund funds;

    @OneToMany(mappedBy = "demandeCredit", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Amortissement> amortissementList = new ArrayList<>();

    public DemandeCredit(float mnttotl, float period) {
        super();
        this.amount=mnttotl;
        this.year=period;

    }
}
