package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.mfb.entity.PackC;

@Repository
public interface PackRepository extends JpaRepository<PackC, Long> {
}
