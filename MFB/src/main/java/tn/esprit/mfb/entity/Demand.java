package tn.esprit.mfb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Demand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "immobilier_id")
    private immobilier immobilier;

    @Column
    private Long amountRequested;

    public Demand() {

    }

    @Override
    public String toString() {
        return "Demand{" +
                "id=" + id +
                ", user=" + user +
                ", immobilier=" + immobilier +
                ", amountRequested=" + amountRequested +
                '}';
    }
}
