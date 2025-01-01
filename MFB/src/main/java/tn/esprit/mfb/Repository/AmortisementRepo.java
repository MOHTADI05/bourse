package tn.esprit.mfb.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.mfb.entity.Amortissement;
import tn.esprit.mfb.entity.User;

import java.util.List;

@Repository
public interface AmortisementRepo extends JpaRepository<Amortissement, Long> {
    List<Amortissement> findByDemandeCreditClient(User user);
}