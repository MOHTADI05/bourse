package tn.esprit.mfb.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.model.BankAccountDTO;
import tn.esprit.mfb.Services.BankAccountService;
import tn.esprit.mfb.util.NotFoundException;
import tn.esprit.mfb.util.ReferencedException;
import tn.esprit.mfb.util.ReferencedWarning;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/bankAccounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class BankAccountResource {

    private final BankAccountService bankAccountService;



    @GetMapping
    public ResponseEntity<List<BankAccountDTO>> getAllBankAccounts() {
        return ResponseEntity.ok(bankAccountService.findAll());
    }

    @GetMapping("/getbyrib/{rib}")
    public ResponseEntity<BankAccountDTO> getBankAccount(
            @PathVariable(name = "rib") final Long rib) {
        return ResponseEntity.ok(bankAccountService.get(rib));
    }


    @PostMapping(value = "/generate-code/{accountId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateAndSetCodeForAccount(@PathVariable Long accountId) {
        try {
            int generatedCode = bankAccountService.generateAndSetCodeForAccount(accountId);

            // Generate QR code image
            String qrCodeData = "Code for account ID " + accountId + ": " + generatedCode;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Map<EncodeHintType, Object> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCodeData, BarcodeFormat.QR_CODE, 200, 200, hintMap);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            byte[] qrCodeBytes = outputStream.toByteArray();

            return ResponseEntity.ok(qrCodeBytes);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (WriterException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(("Error occurred while generating QR code: " + e.getMessage()).getBytes());
        }
    }



    @PostMapping("/add-bonus/{code}")
    public ResponseEntity<String> addBonusToAccount(@PathVariable int code) {
        try {
            bankAccountService.addBonusToAccount(code);
            return ResponseEntity.ok("Bonus added successfully to the account with code: " + code);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while adding bonus: " + e.getMessage());
        }
    }

    @PostMapping("/create")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createBankAccount(
            @RequestBody @Valid final BankAccountDTO bankAccountDTO) {
        final Long createdRib = bankAccountService.create(bankAccountDTO);

        return new ResponseEntity<>(createdRib, HttpStatus.CREATED);
    }

    @PutMapping("/modifier/{rib}")
    public ResponseEntity<Long> updateBankAccount(@PathVariable(name = "rib") final Long rib,
                                                  @RequestBody @Valid final BankAccountDTO bankAccountDTO) {
        bankAccountService.update(rib, bankAccountDTO);
        return ResponseEntity.ok(rib);
    }

    @DeleteMapping("/delete/{rib}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable(name = "rib") final Long rib) {
        final ReferencedWarning referencedWarning = bankAccountService.getReferencedWarning(rib);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        bankAccountService.delete(rib);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/calculateScore/{bankAccountId}")
    public ResponseEntity<String> calculateScore(@PathVariable Long bankAccountId) {
        String message = bankAccountService.calculateScore(bankAccountId);
        return   ResponseEntity.ok("Account ID: "+bankAccountId + message);
    }

    @GetMapping("/calculateTotalBalance")
    public ResponseEntity<Double> calculateTotalBalance() {
        try {
            // Calculate total balance of all bank accounts
            double totalBalance = bankAccountService.calculateTotalBalance();
            return ResponseEntity.ok(totalBalance);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/calculateTotalCashFlow/{numberOfMonths}")
    public ResponseEntity<Integer> calculateTotalCashFlow(@PathVariable int numberOfMonths) {
        try {
            // Calculate total cash flow for the specified number of months
            int totalCashFlow = bankAccountService.calculateTotalCashFlow(numberOfMonths);
            return ResponseEntity.ok(totalCashFlow);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}
