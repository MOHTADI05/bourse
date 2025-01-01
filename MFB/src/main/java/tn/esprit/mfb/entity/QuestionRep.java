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
public class QuestionRep {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_question;

    private String question;

    private String rep1;

    private String rep2;

    private String rep3;

    private String correctRep;

    private Long idQuiz ;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "idQuiz", referencedColumnName = "id_quiz", nullable = false, insertable = false, updatable = false)
    private Quiz quizByIdQuiz ;
}
