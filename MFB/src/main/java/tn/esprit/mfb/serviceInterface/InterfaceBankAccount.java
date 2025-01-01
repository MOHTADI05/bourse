package tn.esprit.mfb.serviceInterface;

import tn.esprit.mfb.entity.BankAccount;
import tn.esprit.mfb.entity.Transaction;
import tn.esprit.mfb.model.BankAccountDTO;
import tn.esprit.mfb.util.ReferencedWarning;

import java.util.List;

public interface InterfaceBankAccount {
    int calculateRiskScore(BankAccount bankAccount);
    void delete(final Long rib);
    List<BankAccountDTO> findAll();
    BankAccountDTO get(final Long rib);
    Long create(final BankAccountDTO bankAccountDTO);
    String calculateScore(Long bankAccountId);
    int countApproachZero(List<Transaction> transactions);
    int calculateTransactionScore(Long bankAccountId);
    int calculateLoyaltyScore(BankAccount bankAccount);
    int calculateBalanceScore(BankAccount bankAccount);
    void update(final Long rib, final BankAccountDTO bankAccountDTO);
    BankAccountDTO mapToDTO(final BankAccount bankAccount,
                            final BankAccountDTO bankAccountDTO);
    void mapToEntity(final BankAccountDTO bankAccountDTO,
                     final BankAccount bankAccount);
    ReferencedWarning getReferencedWarning(final Long rib);

    List<BankAccount> getAllBankAccounts();
}
