package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.Query;
import tn.esprit.mfb.entity.Account;
import tn.esprit.mfb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Account_repo extends JpaRepository<Account,Long> {
    Optional<Account> findByUser(User user);






}
