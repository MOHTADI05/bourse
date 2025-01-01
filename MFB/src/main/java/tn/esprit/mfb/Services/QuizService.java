package tn.esprit.mfb.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.QuizRepository;
import tn.esprit.mfb.entity.Quiz;

import java.util.List;

@Service
@AllArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    public List<Quiz> getAllQuiz() {
        return quizRepository.findAll();
    }

    public Quiz save (Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public void delete (Long id) {
        quizRepository.deleteById(id);
    }

    public Quiz edit (Quiz quiz, Long id) {
        quiz.setId_quiz(id);
        return quizRepository.save(quiz);
    }


    public Quiz findById(Long id) {
        return quizRepository.findById(id).get();
    }
}
