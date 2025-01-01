package tn.esprit.mfb.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Services.inv_history_service;
import tn.esprit.mfb.entity.DemandeCredit;
import tn.esprit.mfb.entity.ProfitLossData;
import tn.esprit.mfb.entity.inv_history;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/inv_history")
public class inv_history_ressource {

    private final inv_history_service Inv_history_service;

    public inv_history_ressource(inv_history_service invHistoryService) {
        this.Inv_history_service  = invHistoryService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<inv_history>> FindAllinv_history() {
        List<inv_history> INV = Inv_history_service.findAllinv_history();
        return new ResponseEntity<>(INV, HttpStatus.OK);
    }

    @GetMapping("/find/{INV_id}")
    public ResponseEntity<inv_history> getinv_historyById(@PathVariable("INV_id") Long id) {
        inv_history HINV = Inv_history_service.findinv_historyById(id);
        return new ResponseEntity<>(HINV, HttpStatus.OK);

    }

    @PostMapping("/add")
    public ResponseEntity<inv_history> addinv_history(@RequestBody inv_history HINV) {
        inv_history newHINV = Inv_history_service.addinv_history(HINV);
        return new ResponseEntity<>(newHINV, HttpStatus.CREATED);

    }

    @PutMapping("/update")
    public ResponseEntity<inv_history> updateInvestisment(@RequestBody inv_history INV) {
        inv_history updateINV = Inv_history_service.updateinv_history(INV);
        return new ResponseEntity<>(updateINV, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{INV_id}")
    public ResponseEntity<?> deleteInv_history(@PathVariable("INV_id") Long id) {
        Inv_history_service.deleteinv_history(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @GetMapping("/daily-profit-loss/{userId}")
    public ResponseEntity<ProfitLossData> getDailyProfitLossForUser(@PathVariable Long userId) {
        Date endDate = new Date(); // Today's date
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1); // One day ago
        Date startDate = cal.getTime();

        ProfitLossData result = Inv_history_service.calculateProfitLossForUser(userId, startDate, endDate);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/weekly-profit-loss/{userId}")
    public ResponseEntity<ProfitLossData> getWeeklyProfitLossForUser(@PathVariable Long userId) {
        Date endDate = new Date(); // Today's date
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, -1); // One week ago
        Date startDate = cal.getTime();

        ProfitLossData result = Inv_history_service.calculateProfitLossForUser(userId, startDate, endDate);
        return ResponseEntity.ok(result);
    }

@GetMapping("/yearly-profit-loss/{userId}")
    public ResponseEntity<ProfitLossData> getYearlyProfitLossForUser(@PathVariable Long userId) {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);

        cal.set(Calendar.YEAR, currentYear);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = cal.getTime();

        Date endDate = new Date();

        ProfitLossData result = Inv_history_service.calculateProfitLossForUser(userId, startDate, endDate);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/findbyclient/{idClient}")
    public List<inv_history> getDemandesCreditParClient(@PathVariable("idClient") Long idClient) {

        return Inv_history_service.findAllCreditByClient(idClient);
    }


}
