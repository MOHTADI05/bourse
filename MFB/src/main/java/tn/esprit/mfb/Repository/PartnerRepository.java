package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.mfb.entity.Partner;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {


}
