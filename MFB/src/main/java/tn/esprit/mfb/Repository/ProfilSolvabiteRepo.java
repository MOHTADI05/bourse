package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.mfb.entity.ProfilSolvabilite;

@Repository
public interface ProfilSolvabiteRepo  extends CrudRepository<ProfilSolvabilite,Long>, JpaRepository<ProfilSolvabilite,Long> {

}
