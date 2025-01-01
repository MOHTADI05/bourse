package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.mfb.entity.BankAccount;
import tn.esprit.mfb.entity.User;

import java.util.Optional;

@Repository

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findFirstByCode(int code);
    BankAccount findByUserCin(Long userCin);

    Optional<BankAccount> findByUser(User user);
}
