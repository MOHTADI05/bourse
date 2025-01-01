package tn.esprit.mfb.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Services.investisment_service;
import tn.esprit.mfb.entity.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/investisment")
public class investisment_ressource {
    private final investisment_service Investisment_service;

    public investisment_ressource(investisment_service investismentService) {
        Investisment_service = investismentService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<investisment>> getAllInvestisment() {
        List<investisment> INV = Investisment_service.findAllInvestismen();
        return new ResponseEntity<>(INV, HttpStatus.OK);

    }

    @GetMapping("/find/{INV_id}")
    public ResponseEntity<investisment> getInvestismentById(@PathVariable("INV_id") Long id) {
        investisment INV = Investisment_service.findInvestismentById(id);
        return new ResponseEntity<>(INV, HttpStatus.OK);

    }

    @PostMapping("/add")
    public ResponseEntity<investisment> createInvestisment(@RequestBody InvestismentDto investismentDto) {
        investisment createdInvestisment = Investisment_service.addInvestisment(investismentDto);
        return new ResponseEntity<>(createdInvestisment, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<investisment> updateInvestisment(@RequestBody investisment INV) {
        investisment updateINV = Investisment_service.updateInvestismen(INV);
        return new ResponseEntity<>(updateINV, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{INV_id}")
    public ResponseEntity<?> deleteInvestisment(@PathVariable("INV_id") Long id) {
        Investisment_service.deleteInvestismen(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @PostMapping("/buy")
    public ResponseEntity<inv_history> buyPercentageInApartment(@RequestBody InvestmentRequest investmentRequest) {
        inv_history history = Investisment_service.buyPercentageInApartment(
                investmentRequest.getImmobilierId(),
                investmentRequest.getCin(),
                investmentRequest.getAmount()
        );
        return new ResponseEntity<>(history, HttpStatus.CREATED);
    }

    @PostMapping("/sell")
    public ResponseEntity<List<inv_history>> sellInvestment(@RequestBody sellInvestment SellInvestment) {
        List<inv_history> historyList = Investisment_service.sellInvestmentByPercentage(
                SellInvestment.getImbId(),
                SellInvestment.getAmount()
        );

        return new ResponseEntity<>(historyList, HttpStatus.CREATED);
    }



    @PostMapping("/sellToDemand/{demandId}/{sellerUserId}")
    public ResponseEntity<?> sellPercentageToDemand(@PathVariable Long demandId, @PathVariable Long sellerUserId) {
        try {
            List<inv_history> historyList = Investisment_service.sellPercentageToDemand(demandId, sellerUserId);
            return ResponseEntity.ok().body(historyList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }






}

























