package tn.esprit.mfb.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Services.QuestionRepService;
import tn.esprit.mfb.Services.QuizService;
import tn.esprit.mfb.entity.QuestionRep;
import tn.esprit.mfb.entity.Quiz;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/questionRep")
public class QuestionRepController {

    private final QuestionRepService questionRepService;
    @GetMapping("/allquest/{idq}")
    public List<QuestionRep> getAllCategorie(@PathVariable Long idq) {
        return questionRepService.getAllQuestionByQuiz(idq);
    }

    @PostMapping("/add")
    public QuestionRep create(@RequestBody QuestionRep questionRep){
        return questionRepService.save(questionRep);
    }

    @PostMapping("/edit/{id}")
    public QuestionRep edit(@RequestBody QuestionRep questionRep,@PathVariable Long id){
        return questionRepService.edit(questionRep,id);
    }
    @GetMapping("/findById/{id}")
    public QuestionRep readById(@PathVariable("id") Long id){
        return questionRepService.findById(id);
    }


    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        questionRepService.delete(id);
    }
}
