package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.mfb.entity.Fund;

@Repository
public interface FundRepository extends JpaRepository<Fund, Long> {
}
