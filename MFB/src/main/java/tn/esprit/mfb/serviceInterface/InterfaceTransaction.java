package tn.esprit.mfb.serviceInterface;

import tn.esprit.mfb.entity.DemandeCredit;
import tn.esprit.mfb.entity.Transaction;
import tn.esprit.mfb.model.BankAccountDTO;
import tn.esprit.mfb.model.TransactionDTO;

public interface InterfaceTransaction {
    Long create(final TransactionDTO transactionDTO);
    void update(final Long transactionId, final TransactionDTO transactionDTO);
    void delete(final Long transactionId);
    Long transferMoney(BankAccountDTO sourceAccountDTO, BankAccountDTO destinationAccountDTO, TransactionDTO transactionDTO);
    Long depositMoney(BankAccountDTO destinationAccountDTO, TransactionDTO transactionDTO) ;
    Transaction mapToEntity(final TransactionDTO transactionDTO,
                            final Transaction transaction);
    TransactionDTO mapToDTO(final Transaction transaction,
                            final TransactionDTO transactionDTO);

    void transferMoneyToFund(long sourceAccountDTO, Long id , DemandeCredit credit);
    }
