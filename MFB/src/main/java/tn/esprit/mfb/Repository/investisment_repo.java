package tn.esprit.mfb.Repository;

import tn.esprit.mfb.entity.immobilier;
import tn.esprit.mfb.entity.investisment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface investisment_repo extends JpaRepository<investisment,Long> {
    List<investisment> findByImb(immobilier imb);



}
