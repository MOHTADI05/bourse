package tn.esprit.mfb.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.CoursRepository;
import tn.esprit.mfb.entity.Cours;

import java.util.List;

@Service
@AllArgsConstructor
public class CoursService {

    private final CoursRepository coursRepository;

    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    public Cours save (Cours cours) {
        return coursRepository.save(cours);
    }

    public void delete (Long id) {
        coursRepository.deleteById(id);
    }

    public Cours edit (Cours cours, Long id) {
        cours.setId_cours(id);
        return coursRepository.save(cours);
    }


    public Cours findById(Long id) {
        return coursRepository.findById(id).get();
    }
}
