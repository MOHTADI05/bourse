package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.mfb.entity.inv_history;

import java.util.Date;
import java.util.List;

public interface inv_history_repo extends JpaRepository<inv_history,Long> {
    @Query("SELECT ih FROM inv_history ih WHERE ih.account.RIB = :rib AND ih.inv_Date BETWEEN :startDate AND :endDate")
    List<inv_history> findTransactionsByAccountRIBAndDateRange(Long rib, Date startDate, Date endDate);
    List<inv_history> findTransactionsByAccountRIB(Long rib);

}
