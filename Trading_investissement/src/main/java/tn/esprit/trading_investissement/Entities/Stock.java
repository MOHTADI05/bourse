package tn.esprit.trading_investissement.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String company;

    @ElementCollection
    private List<Double> currentPrices; // Liste des prix actuels

    private Double initialPrice;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // ou EAGER si vous voulez toujours charger l'utilisateur
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column
    private User user; // Relationship with User entity
}
