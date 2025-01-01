package tn.esprit.mfb.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Services.QuizService;
import tn.esprit.mfb.entity.Quiz;


import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;
    @GetMapping("/allquiz")
    public List<Quiz> getAllCategorie() {
        return quizService.getAllQuiz();
    }

    @PostMapping("/add")
    public Quiz create(@RequestBody Quiz quiz){
        return quizService.save(quiz);
    }

    @PostMapping("/edit/{id}")
    public Quiz edit(@RequestBody Quiz quiz,@PathVariable Long id){
        return quizService.edit(quiz,id);
    }
    @GetMapping("/findById/{id}")
    public Quiz readById(@PathVariable("id") Long id){
        return quizService.findById(id);
    }


    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        quizService.delete(id);
    }
}
