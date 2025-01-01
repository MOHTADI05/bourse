package tn.esprit.mfb.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.Account_repo;
import tn.esprit.mfb.Repository.UserRepository;
import tn.esprit.mfb.Repository.immobilier_repo;
import tn.esprit.mfb.Repository.inv_history_repo;
import tn.esprit.mfb.entity.*;
import tn.esprit.mfb.exeption.AccountNotFoundException;
import tn.esprit.mfb.exeption.ImmobilierNotFoundException;
import tn.esprit.mfb.serviceInterface.InvHistoryServiceImpl;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class inv_history_service implements InvHistoryServiceImpl {
    private final Account_repo accountRepository;
private final immobilier_repo Immobilier_repo ;
private final UserRepository User_repo;
        private final inv_history_repo Inv_history_repo;
    @Autowired
    public inv_history_service(UserRepository userRepo, Account_repo accountRepository, immobilier_repo immobilierRepo, UserRepository userRepo1, inv_history_repo invHistoryRepo) {
        this.accountRepository = accountRepository;
        Immobilier_repo = immobilierRepo;
        User_repo = userRepo1;
        this.Inv_history_repo = invHistoryRepo;
    }






    public inv_history addinv_history(inv_history HIS){
        HIS.setInv_history_repoCode(UUID.randomUUID(),toString());
            return Inv_history_repo.save(HIS);
        }
        public List<inv_history> findAllinv_history(){
            return Inv_history_repo.findAll();
        }


        public inv_history updateinv_history(inv_history HIS){

            return Inv_history_repo.save(HIS);
        }



        public  inv_history findinv_historyById(Long id){

            return Inv_history_repo.findById(id).orElse(null);
        }

        public  void deleteinv_history(Long id){

            Inv_history_repo.deleteById(id);
        }
    private Date getStartOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar.getTime();
    }

    private Date getStartOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar.getTime();
    }

    private Date getStartOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar.getTime();
    }


    public ProfitLossData calculateProfitLossForUser(Long userId,Date startDate,Date endDate) {
        User inv_owner = User_repo.findById(userId).orElseThrow();
        Account investorAccount = accountRepository.findByUser(inv_owner)
                .orElseThrow(() -> new AccountNotFoundException("Account for user " + inv_owner + " not found."));



        TreeMap<String, Double> dailySums = new TreeMap<>(); // TreeMap to keep the dates in order
        initializeDateRange(dailySums, startDate, endDate); // Initialize map with 0.0 for each day

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<inv_history> transactions = Inv_history_repo.findTransactionsByAccountRIBAndDateRange(
                investorAccount.getRIB(), startDate, endDate);

        for (inv_history transaction : transactions) {
            immobilier IMB = Immobilier_repo.findById(transaction.getImb_id())
                    .orElseThrow(() -> new ImmobilierNotFoundException("Immobilier with ID " + transaction.getImb_id() + " not found."));

            double priceChange = (double) (transaction.getAmount() * IMB.getPrixtotlal()) / 100;
            if ("buy".equalsIgnoreCase(transaction.getTransaction_type())) {
                priceChange = -priceChange; // Adjust for buys
            }

            String transactionDate = dateFormat.format(transaction.getInv_Date());
            dailySums.put(transactionDate, dailySums.getOrDefault(transactionDate, 0.0) + priceChange);
        }

        List<Double> prices = new ArrayList<>(dailySums.values());
        List<String> dates = new ArrayList<>(dailySums.keySet());

        return new ProfitLossData(prices, dates);
    }

    private void initializeDateRange(Map<String, Double> map, Date start, Date end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        while (!calendar.getTime().after(end)) {
            map.put(dateFormat.format(calendar.getTime()), 0.0);
            calendar.add(Calendar.DATE, 1);
        }
    }



    public List<inv_history> findAllCreditByClient(Long id_client) {
        User inv_owner = User_repo.findById(id_client).orElseThrow();
        Account investorAccount = accountRepository.findByUser(inv_owner)
                .orElseThrow(() -> new AccountNotFoundException("Account for user " + inv_owner + " not found."));
        long x = investorAccount.getRIB();
        return Inv_history_repo.findTransactionsByAccountRIB(x);
    }





//    public double calculateDailyProfitLossForUser(Long userId) {
//        Date startOfToday = getStartOfToday();
//        return calculateProfitLossForUser(userId, startOfToday, new Date());
//    }
//
//    public double calculateWeeklyProfitLossForUser(Long userId) {
//        Date startOfWeek = getStartOfWeek();
//        return calculateProfitLossForUser(userId, startOfWeek, new Date());
//    }
//
//    public double calculateYearlyProfitLossForUser(Long userId) {
//        Date startOfYear = getStartOfYear();
//        return calculateProfitLossForUser(userId, startOfYear, new Date());
//    }
}
