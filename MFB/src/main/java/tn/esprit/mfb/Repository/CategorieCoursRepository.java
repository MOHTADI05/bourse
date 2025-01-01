package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mfb.entity.CategorieCours;

public interface CategorieCoursRepository extends JpaRepository<CategorieCours, Long> {
}
