package tn.esprit.mfb.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.Account_repo;
import tn.esprit.mfb.Repository.UserRepository;
import tn.esprit.mfb.entity.Account;
import tn.esprit.mfb.entity.User;

import java.util.List;
import java.util.UUID;

@Service
public class Account_service {


    private final Account_repo account_repo;
    private final UserRepository User_repo;
    @Autowired

    public Account_service(Account_repo accountRepo, UserRepository User_repo ) {
       this.account_repo = accountRepo;
       this.User_repo=User_repo;
    }



        public Account addAccount(Account ACC, Long userId){
            User user = User_repo.findById(userId).orElseThrow(null);
            ACC.setUser(user);
            ACC.setAccountCode(UUID.randomUUID(),toString());
            return account_repo.save(ACC);
        }


    public List<Account> findAllAccount(){
            return account_repo.findAll();
        }
        public Account updateAccount(Account ACC){

            return account_repo.save(ACC);
        }



        public  Account findAccountById(Long id){

            return account_repo.findById(id).orElse(null);
        }


        public  void deleteAccount(Long id){

            account_repo.deleteById(id);
        }





    }








