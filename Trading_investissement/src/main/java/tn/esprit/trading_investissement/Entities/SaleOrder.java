package tn.esprit.trading_investissement.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaleOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private Double currentPrice;
    private Integer availableQuantity;
    private LocalDate lastDate;
    private Integer saleQuantity;
    private Double desiredPrice;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // ou EAGER si vous voulez toujours charger l'utilisateur
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column
    private User user; // Relationship with User entity
}
