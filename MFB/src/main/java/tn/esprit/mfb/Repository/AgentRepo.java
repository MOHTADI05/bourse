package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.mfb.entity.TalentReview;
import tn.esprit.mfb.entity.TypeUser;
import tn.esprit.mfb.entity.User;

import java.util.List;

@Repository
public interface AgentRepo extends CrudRepository<User,Long>, JpaRepository<User,Long> {




    List<User> findByRole(TypeUser role);





}
