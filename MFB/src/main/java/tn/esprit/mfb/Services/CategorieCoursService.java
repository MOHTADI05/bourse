package tn.esprit.mfb.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.CategorieCoursRepository;
import tn.esprit.mfb.entity.CategorieCours;

import java.util.List;

@Service
@AllArgsConstructor
public class CategorieCoursService {

    private final CategorieCoursRepository categorieCoursRepository;

    public List<CategorieCours> getAllCategorieCours() {
       return categorieCoursRepository.findAll();
    }

    public CategorieCours save (CategorieCours categorieCours) {
        return categorieCoursRepository.save(categorieCours);
    }

    public void delete (Long id) {
        categorieCoursRepository.deleteById(id);
    }

    public CategorieCours edit (CategorieCours categorieCours, Long id) {
        categorieCours.setId_cat(id);
        return categorieCoursRepository.save(categorieCours);
    }


    public CategorieCours findById(Long id) {
        return categorieCoursRepository.findById(id).get();
    }

}
