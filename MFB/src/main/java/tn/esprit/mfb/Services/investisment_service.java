package tn.esprit.mfb.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.mfb.Repository.*;
import tn.esprit.mfb.entity.*;
import tn.esprit.mfb.exeption.*;
import tn.esprit.mfb.exeption.UserNotFoundException;
import tn.esprit.mfb.serviceInterface.InvestmentServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class investisment_service implements InvestmentServiceImpl {

    private final investisment_repo Investisment_repo;

    private final demande_repo Demande_repo;
    private final Account_repo account_repo;
    @Autowired

    private immobilier_repo Immobilier_repo;

    @Autowired
    private UserRepository User_repo;

    @Autowired
    private inv_history_repo Inv_history_repo;

    @Autowired

    public investisment_service(investisment_repo investismentRepo, demande_repo demandeRepo, Account_repo accountRepo) {
        Investisment_repo = investismentRepo;
        Demande_repo = demandeRepo;
        account_repo = accountRepo;
    }


    @Transactional
    public investisment addInvestisment(InvestismentDto investismentDto) {
        User user = User_repo.findById(investismentDto.getUserId())
                .orElseThrow(() -> new tn.esprit.mfb.exeption.UserNotFoundException("User with ID " + investismentDto.getUserId() + " not found."));
        immobilier immobilier = Immobilier_repo.findById(investismentDto.getImbId())
                .orElseThrow(() -> new ImmobilierNotFoundException("Immobilier with ID " + investismentDto.getImbId() + " not found."));

        investisment investisment = new investisment();
        investisment.setAmount(investismentDto.getAmount());
        investisment.setInv_date(investismentDto.getInvDate());
        investisment.setInv_owner(user);
        investisment.setImb(immobilier);

        return Investisment_repo.save(investisment);
    }

    public List<investisment> findAllInvestismen() {
        return Investisment_repo.findAll();
    }


    public investisment updateInvestismen(investisment employee) {

        return Investisment_repo.save(employee);
    }


    public investisment findInvestismentById(Long id) {

        return Investisment_repo.findById(id).orElse(null);
    }


    public void deleteInvestismen(Long id) {

        Investisment_repo.deleteById(id);
    }

    @Transactional
    public inv_history buyPercentageInApartment(Long imb, Long userId, long amount) {
        User inv_owner = User_repo.findById(userId).orElseThrow(() -> new tn.esprit.mfb.exeption.UserNotFoundException("User with ID " + userId + " not found."));
        immobilier IMB = Immobilier_repo.findById(imb).orElseThrow(() -> new ImmobilierNotFoundException("Immobilier with ID " + imb + " not found."));


        inv_history history = null;
        if (IMB.getRestprix() == 0) {

            Demand demand = new Demand();
            demand.setUser(inv_owner);
            demand.setImmobilier(IMB);
            demand.setAmountRequested(amount);
            Demande_repo.save(demand);
            investisment INV = new investisment(amount, new Date(), imb, inv_owner);

            history = new inv_history(amount, "buy", new Date(), imb, INV);

            return history;
        } else if (IMB.getPourcentage() - amount<0) {

            long availableShares = IMB.getPourcentage();
            long sharesToBuy = Math.min(amount, availableShares);
            long remainingDemand = amount - sharesToBuy;

            long x = (IMB.getPrixtotlal() * sharesToBuy / 100);
            long a = IMB.getRestprix() - x;
            IMB.setPourcentage(availableShares - sharesToBuy);
            IMB.setRestprix(a);

            Account investorAccount = account_repo.findByUser(inv_owner)
                    .orElseThrow(() -> new AccountNotFoundException(
                            "Account for user " + inv_owner + " not found."));
            if (investorAccount.getSolde() < x) {
                throw new InsufficientFundsException("User has insufficient funds for this operation.");
            }
            immobilier X = Immobilier_repo.findById(imb).orElseThrow(() -> new ImmobilierNotFoundException("Immobilier with ID " + imb + " not found."));

            investorAccount.setSolde(investorAccount.getSolde() - x);
            account_repo.save(investorAccount);

            investisment INV = new investisment(sharesToBuy, new Date(), X.getImmobilierId(), inv_owner);
            INV.setImb(IMB);

            Immobilier_repo.save(IMB);

            Investisment_repo.save(INV);
            history = new inv_history(sharesToBuy, "buy", new Date(), X.getImmobilierId(), INV);
            Inv_history_repo.save(history);

            if (remainingDemand > 0) {
                Demand demand = new Demand();
                demand.setUser(inv_owner);
                demand.setImmobilier(IMB);
                demand.setAmountRequested(remainingDemand);
                Demande_repo.save(demand);


            }
        } else {

            long newAvailableShares = IMB.getPourcentage() - amount;
            long x = (IMB.getPrixtotlal() * amount / 100);
            long a = IMB.getRestprix() - x;
            IMB.setPourcentage(newAvailableShares);
            IMB.setRestprix(a);


            Account investorAccount = account_repo.findByUser(inv_owner)
                    .orElseThrow(() -> new AccountNotFoundException(
                            "Account for user " + inv_owner + " not found."));
            if (investorAccount.getSolde() < x) {
                throw new InsufficientFundsException("User has insufficient funds for this operation.");
            } else {
                investorAccount.setSolde(investorAccount.getSolde() - x);
                account_repo.save(investorAccount);


                Immobilier_repo.save(IMB);
                investisment INV = new investisment(amount, new Date(), null, inv_owner);
                INV.setImb(IMB);
                Investisment_repo.save(INV);
                history = new inv_history(amount, "buy", new Date(), imb, INV);
                history.setAccount(investorAccount);
                Inv_history_repo.save(history);
            }
        }
        return history;
    }

    @Transactional
    public List<inv_history> sellInvestmentByPercentage(Long imbId, Long amount) {
        User adminUser = User_repo.findByRole(TypeUser.ADMIN);
        Account accountAdmin = account_repo.findByUser(adminUser)
                .orElseThrow(() -> new AccountNotFoundException("Admin account not found."));
        immobilier IMB = Immobilier_repo.findById(imbId)
                .orElseThrow(() -> new ImmobilierNotFoundException("Immobilier with ID " + imbId + " not found."));
        List<investisment> investments = Investisment_repo.findByImb(IMB);
        List<inv_history> historyList = new ArrayList<>();

        for (investisment investment : investments) {
            double investmentShare = ((double) investment.getAmount() * IMB.getPrixtotlal()) / 100;
            double fundsToAdd = investmentShare + (((double) investment.getAmount() * amount) / 100);
            double adminFee = fundsToAdd / 100.0;
            fundsToAdd -= adminFee;


            Account investorAccount = account_repo.findByUser(investment.getInv_owner())
                    .orElseThrow(() -> new AccountNotFoundException(
                            "Account for user " + investment.getInv_owner().getCin() + " not found."));
            investorAccount.addFunds(fundsToAdd);
            accountAdmin.addFunds(adminFee);

            account_repo.save(investorAccount);
            inv_history history = new inv_history(investment.getAmount(), "sell", new Date(), IMB.getImmobilierId(), investment);
            history.setAccount(investorAccount);
            Inv_history_repo.save(history);
            historyList.add(history);
            investment.setAmount((long) 0);
        }

        return historyList;
    }
    @Transactional
    public List<inv_history> sellPercentageToDemand(Long demandId, Long sellerUserId) {
        User adminUser = User_repo.findByRole(TypeUser.ADMIN);
        Account accountAdmin = account_repo.findByUser(adminUser)
                .orElseThrow(() -> new AccountNotFoundException("Admin account not found."));
        Demand demand = Demande_repo.findById(demandId)
                .orElseThrow(() -> new DemandNotFoundException("Demand not found."));
        User seller = User_repo.findById(sellerUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        immobilier immobilier = demand.getImmobilier();
        List<inv_history> historyList = new ArrayList<>();

        User buyer = demand.getUser();
        Long amount = demand.getAmountRequested();
        investisment sellerInvestment = findInvestmentForSeller(immobilier, seller);
        long x = sellerInvestment.getAmount();
        if (sellerInvestment.getAmount() < amount) {
            throw new InsufficientPercentageException("Seller does not own enough percentage.");
        }
        else {
            double transactionAmount = amount * immobilier.getPrixtotlal() / 100.0;
            double adminFee = transactionAmount / 100.0;
            transactionAmount += adminFee;
            Account buyerAccount = buyer.getACC();
            if (buyerAccount.getSolde() < transactionAmount) {
                throw new InsufficientFundsException("Buyer does not have enough funds.");
            }
            buyerAccount.addFunds(-transactionAmount);
            account_repo.save(buyerAccount);
            Account sellerAccount = seller.getACC();
            sellerAccount.addFunds(transactionAmount-adminFee);
            account_repo.save(sellerAccount);
            sellerInvestment.setAmount(sellerInvestment.getAmount() - amount);
            Investisment_repo.save(sellerInvestment);
            investisment buyerInvestment = null;
            for (investisment inv : immobilier.getInvestments()) {
                if (inv.getInv_owner().getCin()==(buyer.getCin())) {
                    buyerInvestment = inv;
                    break;
                }
            }
            if (buyerInvestment == null) {
                buyerInvestment = new investisment();
                buyerInvestment.setAmount(amount);
                buyerInvestment.setInv_owner(buyer);
                buyerInvestment.setInv_date(new Date());
                buyerInvestment.setImb(immobilier);
                immobilier.getInvestments().add(buyerInvestment);
            } else {
                buyerInvestment.setAmount(buyerInvestment.getAmount() + amount);
            }
            double S = 2 * adminFee;
            accountAdmin.addFunds(S);

            inv_history H = new inv_history(x, "sell", new Date(), immobilier.getImmobilierId(), sellerInvestment);
            H.setAccount(sellerAccount);

            Inv_history_repo.save(H);
            inv_history HisS = new inv_history((long)1, "Sell-fee", new Date(), immobilier.getImmobilierId(), buyerInvestment);
            HisS.setAccount(buyerAccount);


            Inv_history_repo.save(HisS);

            inv_history history = new inv_history(buyerInvestment.getAmount(), "buy", new Date(), immobilier.getImmobilierId(), buyerInvestment);
            history.setAccount(buyerAccount);
            Inv_history_repo.save(history);
            inv_history his = new inv_history((long)1, "Buy-fee", new Date(), immobilier.getImmobilierId(), buyerInvestment);
            his.setAccount(buyerAccount);
            Inv_history_repo.save(his);

            Investisment_repo.save(buyerInvestment);
            Demande_repo.delete(demand);
            historyList.add(history);
            historyList.add(his);

            return historyList;
        }
    }
    private investisment findInvestmentForSeller(immobilier immobilier, User seller) throws InvestismentNotFoundException {

        for (investisment inv : immobilier.getInvestments()) {
            if (Objects.equals(inv.getInv_owner().getCin(), seller.getCin())) {
                return inv;
            }
        }

        throw new InvestismentNotFoundException("Investment not found for seller ID " + seller.getCin());
    }




}











