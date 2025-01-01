package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mfb.entity.Demand;

import java.util.List;

public interface demande_repo extends JpaRepository<Demand,Long> {
    List<Demand> findByImmobilierImmobilierId(Long immobilierId);

}
