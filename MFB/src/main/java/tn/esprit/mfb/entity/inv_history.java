package tn.esprit.mfb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
public class inv_history implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)


        private Long H_id;
        private Long amount;
        private String transaction_type;
        private Date inv_Date;
        private Long imb_id;

        @ManyToOne(fetch = FetchType.LAZY)
       @JsonIgnore
        @JoinColumn(name = "account_id")
        private Account account;
        public inv_history() {
        }
        @ManyToOne(fetch = FetchType.LAZY)
        @JsonIgnore
        @JsonManagedReference
        @JoinColumn(name = "his_id")
        private investisment his;

        public inv_history(Long amount, String transaction_type, Date inv_date, Long imb_id, investisment his) {
                this.amount = amount;
                this.transaction_type = transaction_type;
                this.inv_Date = inv_date;
                this.imb_id = imb_id;
                this.his = his;
        }

        @Override
        public String toString() {
                return "inv_history{" +
                        "H_id=" + H_id +
                        ", amount=" + amount +
                        ", transaction_type='" + transaction_type + '\'' +
                        ", inv_Date=" + inv_Date +
                        ", imb_id=" + imb_id +
                        ", account=" + account +
                        ", his=" + his +
                        '}';
        }



        public void setInv_history_repoCode(UUID uuid, String string) {
    }
}
