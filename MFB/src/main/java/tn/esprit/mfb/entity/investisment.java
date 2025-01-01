package tn.esprit.mfb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
public class investisment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long INV_id;
    private Long amount ;
    private Date inv_date ;
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User inv_owner;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imb_id")
@JsonManagedReference
    private immobilier imb;


    @OneToMany(mappedBy = "his",cascade = CascadeType.ALL, orphanRemoval = true)
@JsonBackReference


    private Set<inv_history> invH;


    public investisment() {

    }

    public investisment(Long amount, Date inv_date, Long imb_id, User inv_owner) {
        this.amount = amount;
        this.inv_date = inv_date;

        this.inv_owner = inv_owner;
    }

    @Override
    public String toString() {
        return "investisment{" +
                "INV_id=" + INV_id +
                ", amount=" + amount +
                ", inv_date=" + inv_date +
                ", inv_owner=" + (inv_owner != null ? inv_owner.getCin() : "null") +
                ", imb=" + (imb != null ? imb.getImmobilierId() : "null") +
                // ... any other fields you want to include, but be careful of relationships
                '}';
    }

    public void setinvestismentCode(UUID uuid, String string) {
    }


}

