package tn.esprit.mfb.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.entity.Fund;
import tn.esprit.mfb.serviceInterface.IfundService;

import java.util.List;

@RestController
@RequestMapping("Fund")
@AllArgsConstructor
@CrossOrigin("*")

public class FundController {
private final IfundService fundService;
    @GetMapping("/retrieve-all-funds")
    @ResponseBody
    public List<Fund> getFund() {
        List<Fund> listFund = fundService.retrieveAllFunds();
        return listFund;
    }

    // http://localhost:8083/BKFIN/Fund/retrieve-fund/1
    @GetMapping("/retrieve-fund/{id}")
    @ResponseBody
    public Fund retrieveFund(@PathVariable("id") Long fundId) {
        return fundService.retrieveFund(fundId);
    }

    // http://localhost:8083/BKFIN/Fund/add-fund
    @PostMapping("/add-fund")
    @ResponseBody
    public Fund addFund(@RequestBody Fund f)
    {
        Fund fund = fundService.addFund(f);
        return fund;
    }


    // http://localhost:8083/BKFIN/Fund/remove-fund/3
    @DeleteMapping("/remove-fund/{id}")
    public void removeFund(@PathVariable("id") Long fundId) {
        fundService.deleteFund(fundId);
    }

    // http://localhost:8083/BKFIN/Fund/modify-fund
    @PutMapping("/modify-fund")
    public Fund modifyFund(@RequestBody Fund fund) {
        return fundService.updateFund(fund);
    }

}
