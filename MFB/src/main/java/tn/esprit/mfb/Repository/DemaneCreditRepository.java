package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.mfb.entity.DemandeCredit;

import java.util.List;
@Repository
public interface DemaneCreditRepository extends JpaRepository<DemandeCredit, Long> {

    List<DemandeCredit> findByClientCinAndCompletedIsFalseAndStateIsFalse(Long clientId);
    List<DemandeCredit> findByClientCinAndStateIsFalse(Long id);
    DemandeCredit findByClientCinAndCompletedIsTrueAndStateIsTrue(Long id);
    DemandeCredit  findTopByClientCinAndCompletedIsTrueAndStateIsTrueOrderByObtainingdateDesc(Long clientId);

    List<DemandeCredit>findByClientCin(Long clientId);
}
