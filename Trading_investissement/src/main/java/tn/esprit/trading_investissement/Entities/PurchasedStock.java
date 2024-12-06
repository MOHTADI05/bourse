package tn.esprit.trading_investissement.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PurchasedStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;
    private Double price;
    private Integer quantity;

    @Transient // Not stored in DB, calculated dynamically
    private Double currentPrice;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // ou EAGER si vous voulez toujours charger l'utilisateur
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column
    private User user; // Relationship with User entity
}
