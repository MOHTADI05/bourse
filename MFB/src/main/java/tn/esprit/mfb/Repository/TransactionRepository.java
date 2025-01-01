package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.mfb.entity.BankAccount;
import tn.esprit.mfb.entity.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    Transaction findFirstBySourceRIB(BankAccount bankAccount);

    List<Transaction> findBySourceRIBRib(Long bankAccountId);

    List<Transaction> findBySourceRIB_Rib(Long sourceRIB);
}
