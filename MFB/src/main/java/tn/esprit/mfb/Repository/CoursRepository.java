package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mfb.entity.Cours;

public interface CoursRepository extends JpaRepository<Cours, Long> {
}
