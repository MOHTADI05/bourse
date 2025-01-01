package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.mfb.entity.HistoryC;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface HistoryCRepository extends JpaRepository<HistoryC, Long> {

    List<HistoryC> findByDemandecreditsIdDemandecredit(Long id);

    @Query("SELECT MAX(h.SupposedDate) FROM HistoryC h WHERE h.demandecredits.idDemandecredit = :idcredit")
    LocalDate findMaxSupposedDateByDemandecreditsIdDemandecredit(@Param("idcredit") Long idcredit);


}
