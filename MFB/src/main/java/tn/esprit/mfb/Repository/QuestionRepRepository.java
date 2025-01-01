package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mfb.entity.QuestionRep;

import java.util.List;

public interface QuestionRepRepository extends JpaRepository<QuestionRep,Long> {

    List<QuestionRep> queryQuestionRepByIdQuiz(Long idQuiz);
}
