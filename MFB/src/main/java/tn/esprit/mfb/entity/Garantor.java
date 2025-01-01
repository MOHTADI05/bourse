package tn.esprit.mfb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Garantor implements Serializable {
    @Id
    @Column(name ="idGarantor")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idGarantor ;
    private String nameGarantor ;
    private String secondnameGarantor ;
    private float salaryGarantor ;
    private String workGarantor ;
    private String urlimage ;
    @JsonIgnore
    @OneToOne
    private DemandeCredit demandecredit;
}
