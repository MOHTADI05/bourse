package tn.esprit.mfb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor

public class immobilier implements Serializable {
       @Id
        @Column(nullable = false, updatable = false)
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long immobilierId;

        @Column
        private String name;

        @Column
        private String description;
        @Column
        private Long pourcentage;

        @Column
        private LocalDate foundingDate;

        @Column
        private String location;
        @Column
        private Long prixtotlal;
        @Column
        private Long restprix;



 @OneToMany(mappedBy = "imb", cascade = CascadeType.ALL)
@JsonBackReference
 @JsonIgnore

 private Set<investisment> investments;





 public immobilier() {

 }

    @Override
    public String toString() {
        return "immobilier{" +
                "immobilierId=" + immobilierId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pourcentage=" + pourcentage +
                ", foundingDate=" + foundingDate +
                ", location='" + location + '\'' +
                ", prixtotlal=" + prixtotlal +
                ", restprix=" + restprix +
                '}';
    }


 public void setimmobilierCode(UUID uuid, String string) {
 }

}
