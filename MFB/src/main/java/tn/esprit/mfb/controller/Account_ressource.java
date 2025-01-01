package tn.esprit.mfb.controller;

import tn.esprit.mfb.Services.Account_service;
import tn.esprit.mfb.Services.UserService;
import tn.esprit.mfb.entity.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Account")
@CrossOrigin("*")

public class Account_ressource {



    private final Account_service accountService;


    public Account_ressource(Account_service accountService, UserService userService) {
        this.accountService = accountService;

    }


    @GetMapping("/all")
        public ResponseEntity<List<Account>> getAllAccount() {
            List<Account> FAV = accountService.findAllAccount();
            return new ResponseEntity<>(FAV, HttpStatus.OK);
        }

        @GetMapping("/find/{RIB}")
        public ResponseEntity<Account> getFavoriteById(@PathVariable("RIB") Long id) {
            Account INV = accountService.findAccountById(id);
            return new ResponseEntity<>(INV, HttpStatus.OK);

        }

    @PostMapping("/add/{userId}")
    public ResponseEntity<Account> addAccount(@RequestBody Account account, @PathVariable Long userId) {
        Account newAccount = accountService.addAccount(account, userId);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }


    @PutMapping("/update")
        public ResponseEntity<Account> updateAccount(@RequestBody Account ACC) {
            Account updateINV = accountService.updateAccount(ACC);
            return new ResponseEntity<>(updateINV, HttpStatus.OK);

        }

        @DeleteMapping("/delete/{RIB}")
        public ResponseEntity<?> deleteAccount(@PathVariable("RIB") Long RIB) {
            accountService.deleteAccount(RIB);
            return new ResponseEntity<>(HttpStatus.OK);

        }

    }








