package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.mfb.entity.Garantor;

@Repository
public interface GarantorRepo extends JpaRepository<Garantor, Long> {
}
