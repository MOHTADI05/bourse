package tn.esprit.trading_investissement.Entities;

import jakarta.persistence.*;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleOrder> saleOrders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchasedStock> purchasedStocks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stock> stocks;



    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms; // Liste des salles créées par cet utilisateur

}