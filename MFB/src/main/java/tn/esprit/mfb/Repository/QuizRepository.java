package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mfb.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
