package tn.esprit.mfb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cours;

    private String nom;

    private int nombreH;

    private String description;

    private Long id_cat ;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_cat", referencedColumnName = "id_cat", nullable = false, insertable = false, updatable = false)
    private CategorieCours categorieCoursByIdCat ;
}
