package tn.esprit.mfb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tn.esprit.mfb.entity.clusterenum.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class ProfilSolvabilite implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double revenuMensuelNet;

    private double chargeMensuel;

    private double montantDetteTotale;

    private double ratioDetteRevenu;

    private int ancienneteEmploiActuel;

    private double epargneActifsLiquides;

    @Enumerated(EnumType.STRING)
    private StatutEmploi statutEmploi;

    private int chargeFamille;

    @Enumerated(EnumType.STRING)
    private NiveauEdu niveauEducation;

    private int age;

    @Enumerated(EnumType.STRING)
    private Region regionResidence;

    @Enumerated(EnumType.STRING)
    private Logement typeLogement;

    @Enumerated(EnumType.STRING)
    private Matrimoine situationMatrimoniale;



}
