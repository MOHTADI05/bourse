package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.mfb.entity.CoursDoc;

public interface CoursDocReposioty extends JpaRepository<CoursDoc, Integer> {

    @Query("SELECT MAX(e.ordre) FROM CoursDoc e")
    Integer  findMaxValue();
}
