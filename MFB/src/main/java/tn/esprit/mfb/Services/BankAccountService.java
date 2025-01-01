package tn.esprit.mfb.Services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.BankAccountRepository;
import tn.esprit.mfb.Repository.TransactionRepository;
import tn.esprit.mfb.Repository.UserRepository;
import tn.esprit.mfb.entity.BankAccount;
import tn.esprit.mfb.entity.Transaction;
import tn.esprit.mfb.entity.User;
import tn.esprit.mfb.model.BankAccountDTO;
import tn.esprit.mfb.serviceInterface.InterfaceBankAccount;
import tn.esprit.mfb.util.NotFoundException;
import tn.esprit.mfb.util.ReferencedWarning;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BankAccountService implements InterfaceBankAccount {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;



    public List<BankAccountDTO> findAll() {
        final List<BankAccount> bankAccounts = bankAccountRepository.findAll(Sort.by("rib"));
        return bankAccounts.stream()
                .map(bankAccount -> mapToDTO(bankAccount, new BankAccountDTO()))
                .toList();
    }

    public BankAccountDTO get(final Long rib) {
        return bankAccountRepository.findById(rib)
                .map(bankAccount -> mapToDTO(bankAccount, new BankAccountDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BankAccountDTO bankAccountDTO) {
        final BankAccount bankAccount = new BankAccount();
        mapToEntity(bankAccountDTO, bankAccount);
        return bankAccountRepository.save(bankAccount).getRib();
    }

    public String calculateScore(Long bankAccountId) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId).orElse(null);
        if (bankAccount == null) {
            return "Bank account not found";
        }

        // Calculate transaction score
        int transactionScore = calculateTransactionScore(bankAccountId);

        // Calculate loyalty score
        int loyaltyScore = calculateLoyaltyScore(bankAccount);

        // Calculate balance score
        int balanceScore = calculateBalanceScore(bankAccount);

        int riskScore = calculateRiskScore(bankAccount);

        return "The balance score is " + balanceScore + ". The Loyalty Score is " + loyaltyScore + ". The transaction score is " + transactionScore + ". The risk score is " + riskScore + ".";
    }

    public int calculateRiskScore(BankAccount bankAccount) {
        double balance = bankAccount.getBalance();
        List<Transaction> transactions = transactionRepository.findBySourceRIBRib(bankAccount.getRib());
        int approachZeroCount = countApproachZero(transactions);

        // Calculate risk score based on balance, transactions, and approach to zero
        int riskScore = 0;

        // If balance is negative, increase risk score
        if (balance < 0) {
            riskScore += 5; // Adjust based on severity of negative balance
        }

        // Increase risk score based on number of transactions
        int totalTransactions = transactions.size();
        riskScore += totalTransactions / 10; // Adjust as needed

        // Increase risk score based on frequency of approaching zero balance
        riskScore += approachZeroCount;

        return riskScore;
    }

    public int countApproachZero(List<Transaction> transactions) {
        // Initialize the counter for transactions approaching zero balance
        int approachZeroCount = 0;

        // Initialize the current balance
        double balance = 0;

        // Iterate through each transaction in the list
        for (Transaction transaction : transactions) {
            // Update the balance by adding the amount of the current transaction
            balance += transaction.getAmount();

            // Check if the updated balance is less than the threshold (adjustable as needed)
            if (balance < 100) { // Adjust this threshold as needed
                // If the balance approaches zero, increment the counter
                approachZeroCount++;
            }
        }

        // Return the total count of transactions approaching zero balance
        return approachZeroCount;
    }

    public int calculateTransactionScore(Long bankAccountId) {
        List<Transaction> transactions = transactionRepository.findBySourceRIBRib(bankAccountId);
        int totalTransactions = transactions.size();
        int minTransactions = 0;
        int maxTransactions = 100;
        int minScore = 0;
        int maxScore = 10;
        // Calculate the transaction score using a linear transformation
        // Normalize the total number of transactions between minTransactions and maxTransactions
        // Map this normalized value to a score between minScore and maxScore
        int transactionScore = (int) Math.round(((double) (totalTransactions - minTransactions) / (maxTransactions - minTransactions)) * (maxScore - minScore) + minScore);
        // Ensure that the calculated score falls within the defined score range
        return Math.min(maxScore, Math.max(minScore, transactionScore));
    }

    public int calculateLoyaltyScore(BankAccount bankAccount) {
        LocalDate accountCreationDate = bankAccount.getOpenDate();
        LocalDate currentDate = LocalDate.now();
        long yearsDifference = ChronoUnit.YEARS.between(accountCreationDate, currentDate);
        if (yearsDifference >= 10) {
            return 10; // Highest score
        } else if (yearsDifference >= 5) {
            return 8;
        } else if (yearsDifference >= 3) {
            return 6;
        } else if (yearsDifference >= 1) {
            return 4;
        } else {
            return 2; // Account created less than a year ago
        }
    }

    public int calculateBalanceScore(BankAccount bankAccount) {
        double balance = bankAccount.getBalance();
        if (balance >= 10000) {
            return 10; // Highest score
        } else if (balance >= 5000) {
            return 8;
        } else if (balance >= 1000) {
            return 6;
        } else if (balance >= 100) {
            return 4;
        } else if (balance >= 0) {
            return 2;
        } else {
            return 0; // Negative balance, lowest score
        }
    }
    public void update(final Long rib, final BankAccountDTO bankAccountDTO) {
        final BankAccount bankAccount = bankAccountRepository.findById(rib)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bankAccountDTO, bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public void delete(final Long rib) {
        bankAccountRepository.deleteById(rib);
    }



    public int calculateTotalCashFlow(int numberOfMonths) {
        int totalCashFlow = 0;
        LocalDate currentDate = LocalDate.now();
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();

        for (BankAccount bankAccount : bankAccounts) {
            // Assuming you have a method in BankAccount to get the balance
            double balance = bankAccount.getBalance();

            // Assuming you have a method in BankAccount to get the open date
            LocalDate accountCreationDate = bankAccount.getOpenDate();

            // Calculate the difference in months between the account creation date and the current date
            long monthsDifference = ChronoUnit.MONTHS.between(accountCreationDate, currentDate);

            // Check if the difference is less than or equal to the specified number of months
            if (monthsDifference <= numberOfMonths) {
                totalCashFlow += (int) balance;
            }
        }

        return totalCashFlow;
    }


    public BankAccountDTO mapToDTO(final BankAccount bankAccount,
                                   final BankAccountDTO bankAccountDTO) {
        bankAccountDTO.setRib(bankAccount.getRib());
        bankAccountDTO.setBalance(bankAccount.getBalance());
        bankAccountDTO.setOpenDate(bankAccount.getOpenDate());
        bankAccountDTO.setCode(bankAccount.getCode());
        bankAccountDTO.setLoyaltyScore(bankAccount.getLoyaltyScore());
        bankAccountDTO.setTypeAccount(bankAccount.getTypeAccount());
        bankAccountDTO.setUser(bankAccount.getUser() == null ? null : bankAccount.getUser().getCin());
        return bankAccountDTO;
    }


    public void mapToEntity(final BankAccountDTO bankAccountDTO,
                            final BankAccount bankAccount) {
        bankAccount.setBalance(bankAccountDTO.getBalance());
        bankAccount.setOpenDate(bankAccountDTO.getOpenDate());
        bankAccount.setCode(bankAccountDTO.getCode());
        bankAccount.setLoyaltyScore(bankAccountDTO.getLoyaltyScore());
        bankAccount.setTypeAccount(bankAccountDTO.getTypeAccount());
        final User user = bankAccountDTO.getUser() == null ? null : userRepository.findById(bankAccountDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        bankAccount.setUser(user);
    }



    public void addBonusToAccount(int code) {
        // Retrieve bank account ID by code
        Optional<BankAccount> accountIdOptional = bankAccountRepository.findFirstByCode(code);

        if (accountIdOptional.isPresent()) {
            Long accountId = accountIdOptional.get().getRib();
            // Retrieve bank account by ID
            BankAccountDTO bankAccountDTO = get(accountId);

            // Add bonus to the account's balance
            int loyaltyscore=bankAccountDTO.getLoyaltyScore();
            double currentBalance = bankAccountDTO.getBalance();
            bankAccountDTO.setBalance(currentBalance + 50);
            bankAccountDTO.setLoyaltyScore(loyaltyscore + 50);

            // Update the account
            update(accountId,bankAccountDTO);
        } else {
            throw new NotFoundException("Account with code " + code + " not found");
        }
    }

    public int generateAndSetCodeForAccount(Long accountId) {
        // Retrieve bank account using the provided ID
        BankAccountDTO bankAccountDTO = get(accountId);

        // Generate a random code between 100 and 1000
        int minCode = 100;
        int maxCode = 1000;
        int generatedCode = (int) (Math.random() * (maxCode - minCode + 1)) + minCode;

        // Set the generated code to the bankAccountDTO
        bankAccountDTO.setCode(generatedCode);

        // Save the updated bank account
        update(accountId,bankAccountDTO);
        return generatedCode;
    }



    public ReferencedWarning getReferencedWarning(final Long rib) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final BankAccount bankAccount = bankAccountRepository.findById(rib)
                .orElseThrow(NotFoundException::new);
        final Transaction sourceRIBTransaction = transactionRepository.findFirstBySourceRIB(bankAccount);
        if (sourceRIBTransaction != null) {
            referencedWarning.setKey("bankAccount.transaction.sourceRIB.referenced");
            referencedWarning.addParam(sourceRIBTransaction.getTransactionId());
            return referencedWarning;
        }
        return null;
    }

    @Override
    public List<BankAccount> getAllBankAccounts() {
        return null;
    }

    public double getBalance(Long userId) {

        BankAccount userAccount = bankAccountRepository.findByUserCin(userId);

        // Vérifier si le compte bancaire existe
        if (userAccount != null) {
            return userAccount.getBalance();
        } else {
            // Gérer le cas où aucun compte bancaire n'est trouvé pour cet utilisateur
            throw new RuntimeException("Aucun compte bancaire trouvé pour l'utilisateur avec l'ID : " + userId);
        }
    }

    public void deductUserBalance(Long userId, double amount) throws InsufficientBalanceException, UserNotFoundException {
        BankAccount userAccount = bankAccountRepository.findByUserCin(userId);
        if (userAccount != null) {
            double currentBalance = userAccount.getBalance();
            if (currentBalance >= amount) {
                userAccount.setBalance(currentBalance - amount);
                bankAccountRepository.save(userAccount);
            } else {
                // Gérer le cas où le solde de l'utilisateur est insuffisant pour effectuer la déduction
                throw new InsufficientBalanceException("Solde insuffisant pour l'utilisateur avec l'ID : " + userId);
            }
        } else {
            // Gérer le cas où l'utilisateur n'est pas trouvé, par exemple en levant une exception ou en effectuant une autre action appropriée
            throw new UserNotFoundException("Utilisateur non trouvé avec l'ID : " + userId);
        }
    }

    public double calculateTotalBalance() { // Calculate total balance of all bank accounts
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        double totalBalance = 0;
        for (BankAccount bankAccount : bankAccounts) {
            totalBalance += bankAccount.getBalance();
        }
        return totalBalance;
    }
}

