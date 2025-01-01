package tn.esprit.mfb.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.QuestionRepRepository;
import tn.esprit.mfb.Repository.QuizRepository;
import tn.esprit.mfb.entity.QuestionRep;
import tn.esprit.mfb.entity.Quiz;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionRepService {

    private final QuestionRepRepository questionRepRepository;

    public List<QuestionRep> getAllQuestionByQuiz(Long idQuiz) {
        return questionRepRepository.queryQuestionRepByIdQuiz(idQuiz);
    }

    public QuestionRep save (QuestionRep quiz) {
        return questionRepRepository.save(quiz);
    }

    public void delete (Long id) {
        questionRepRepository.deleteById(id);
    }

    public QuestionRep edit (QuestionRep quiz, Long id) {
        quiz.setId_question(id);
        return questionRepRepository.save(quiz);
    }


    public QuestionRep findById(Long id) {
        return questionRepRepository.findById(id).get();
    }
}
