package tn.esprit.mfb.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.entity.Credit;
import tn.esprit.mfb.serviceInterface.ICreditService;

import java.util.List;

@RestController
@RequestMapping("/Credit")
@AllArgsConstructor
@CrossOrigin("*")

public class CreditController {
    private final ICreditService creditService;

    @PostMapping("/create")
    public Credit create(@RequestBody Credit credit){
        return creditService.addCredit(credit);
    }
    @GetMapping("/read/{id}")
    public Credit getCreditById(@PathVariable("id") Long id){
        return creditService.getCreditById(id);
    }
    @GetMapping("/read")
    public List<Credit> read(){
        return creditService.AllCredit();
    }
    @GetMapping("/readCreditPack/{id}")
    public List<Credit> readCreditPack(@PathVariable Long id){
        return creditService.AllCreditByPack(id);
    }

@PutMapping ("/update/{id}")
    public Credit update(@PathVariable Long id,@RequestBody Credit credit){
        return creditService.updateCredit(id , credit);
    }

    @DeleteMapping ("/delete/{id}")
    public String delete(@PathVariable Long id)
    {
        return creditService.deleteCredit(id);
    }

    @PostMapping("/add/{idP}")
    public Credit addRegistrationAndAssignToSkier(@RequestBody Credit credit, @PathVariable("idP") Long idP) {
        return creditService.addCreditAndAssignToPackC( credit,idP);
    }




}
