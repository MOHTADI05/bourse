package tn.esprit.mfb.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Services.ExcelService;

@RestController
@RequestMapping(value = "/api/Excel", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")

public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @GetMapping("/generateExcel/{bankAccountId}")
    public ResponseEntity<String> generateExcel(@PathVariable Long bankAccountId) {
        excelService.generateExcelForBankAccount(bankAccountId);
        return ResponseEntity.ok("excel file generated successfully " +
                "file name is transactions_" + bankAccountId + ".xls");
    }
}
