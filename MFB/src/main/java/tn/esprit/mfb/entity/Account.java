package tn.esprit.mfb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@Entity

    public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long RIB ;
    private double solde ;
    private Date open_date ;
    private Date   expiration_date ;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<inv_history> investmentHistory;


   @OneToOne
   private User user;

     public Account() {

    }



    @Override
    public String toString() {
        return "Account{" +
                "RIB=" + RIB +
                ", Solde=" + solde +
                ", open_date=" + open_date +
                ", expiration_date=" + expiration_date +
                '}';
    }

    public void setAccountCode(UUID uuid, String string) {
    }
    public void addFunds(double amount) {
        this.solde += amount;
    }




}
