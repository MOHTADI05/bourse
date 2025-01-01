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
public class Quiz {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_quiz;
    private String quiz_name;
    private String quiz_description;
    private Long id_cat ;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_cat", referencedColumnName = "id_cat", nullable = false, insertable = false, updatable = false)
    private CategorieCours categorieCoursByIdCat ;

}
