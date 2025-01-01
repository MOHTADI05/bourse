package tn.esprit.mfb.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Services.CategorieCoursService;
import tn.esprit.mfb.entity.CategorieCours;
import tn.esprit.mfb.entity.PackC;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/categorieCours")
public class CategorieCoursController {

    CategorieCoursService categorieCoursService;
    @GetMapping("/allCategorie")
    public List<CategorieCours> getAllCategorie() {
        return categorieCoursService.getAllCategorieCours();
    }

    @PostMapping("/add")
    public CategorieCours create(@RequestBody CategorieCours categorieCours){
        return categorieCoursService.save(categorieCours);
    }

    @PostMapping("/edit/{id}")
    public CategorieCours edit(@RequestBody CategorieCours categorieCours,@PathVariable Long id){
        return categorieCoursService.edit(categorieCours,id);
    }

    @GetMapping("/findById/{id}")
    public CategorieCours readById(@PathVariable("id") Long id){
        return categorieCoursService.findById(id);
    }


    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
         categorieCoursService.delete(id);
    }
}
