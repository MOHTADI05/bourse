package tn.esprit.mfb.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Services.CoursService;
import tn.esprit.mfb.entity.Cours;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/cours")
public class CoursController {

    private final CoursService coursService;
    @GetMapping("/allcours")
    public List<Cours> getAllCategorie() {
        return coursService.getAllCours();
    }

    @PostMapping("/add")
    public Cours create(@RequestBody Cours cours){
        return coursService.save(cours);
    }

    @PostMapping("/edit/{id}")
    public Cours edit(@RequestBody Cours cours,@PathVariable Long id){
        return coursService.edit(cours,id);
    }
    @GetMapping("/findById/{id}")
    public Cours readById(@PathVariable("id") Long id){
        return coursService.findById(id);
    }


    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        coursService.delete(id);
    }
}
