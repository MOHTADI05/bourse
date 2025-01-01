package tn.esprit.mfb.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Repository.BankAccountRepository;
import tn.esprit.mfb.Services.*;
import tn.esprit.mfb.entity.Amortization;
import tn.esprit.mfb.entity.BankAccount;
import tn.esprit.mfb.entity.Payment;

import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
@CrossOrigin("*")
public class PaymentController {


    private PaymentServiceImpl paymentService;
    private AmortizationServiceImpl amortizationService;
    private UserService userService ;
    private BankAccountService bankAccountService;
    private BankAccountRepository bankAccountRepository;

    @GetMapping("/export/excel/{mnttotl}/{period}/{interst}")
    @ResponseBody
    public void exportToExcel(HttpServletResponse response, @PathVariable("mnttotl") float mnttotl, @PathVariable("period") float period,
                              @PathVariable("interst") float interst) throws IOException {

        // Créer un amortissement avec les attributs donnés
        Amortization amortization = new Amortization();
        amortization.setStartDate(new Date()); // Vous devez définir la date de début appropriée
        amortization.setInitialBalance(mnttotl);
        amortization.setDurationInMonths((int) period);
        amortization.setInterestRate(interst);
        amortization.setPaymentType(0); // Vous devez définir le type de paiement approprié
        amortization.setFutureValue(0); // Vous devez définir la valeur future appropriée

        // Initialisez les champs inconnus de l'amortissement
        amortizationService.initializeUnknownFields(amortization);

        // Récupérer la liste des paiements de l'amortissement
        List<Payment> paymentList = amortization.getPaymentList();

        // Exporter la liste des paiements au format Excel
        UserExcelExporter userExcelExporter = new UserExcelExporter(paymentList);
        userExcelExporter.export(response);
    }


    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/get/{paymentNumber}")
    public ResponseEntity<Payment> getPaymentByNumber(@PathVariable int paymentNumber) {
        Payment payment = paymentService.getPaymentByNumber(paymentNumber);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
    @GetMapping("/payments/{mnttotl}/{period}/{interst}")
    @ResponseBody
    public ResponseEntity<List<Payment>> getPayments(@PathVariable("mnttotl") float mnttotl,
                                                     @PathVariable("period") float period,
                                                     @PathVariable("interst") float interst) {

        // Create an amortization with the given attributes
        Amortization amortization = new Amortization();
        amortization.setStartDate(new Date()); // You must set appropriate start date
        amortization.setInitialBalance(mnttotl);
        amortization.setDurationInMonths((int) period);
        amortization.setInterestRate(interst);
        amortization.setPaymentType(0); // You must set appropriate payment type
        amortization.setFutureValue(0); // You must set appropriate future value

        // Initialize unknown fields of the amortization
        amortizationService.initializeUnknownFields(amortization);

        // Retrieve the list of payments from the amortization
        List<Payment> paymentList = amortization.getPaymentList();

        // Return the list of payments in the response
        return ResponseEntity.ok(paymentList);
    }


    @GetMapping("/{paymentNumber}/confirm")
    public ResponseEntity<Payment> confirmPayment(@PathVariable int paymentNumber) {
        Payment confirmedPayment = paymentService.confirmPayment(paymentNumber);
        return ResponseEntity.ok(confirmedPayment);
    }


    @PostMapping("/create")
    public ResponseEntity<InputStreamResource> createPayment(@RequestBody @Valid Payment payment) {
        // Créer le paiement
        Payment createdPayment = paymentService.createPayment(payment);

        // Vérifier si le solde du compte de l'utilisateur est suffisant
        double paymentAmount = createdPayment.getPrincipalPaid();
        double userBalance = 0; // Supposons qu'il y ait un service pour récupérer le solde de l'utilisateur
        userBalance = bankAccountService.getBalance(createdPayment.getUser().getCin());
        if (userBalance >= paymentAmount) {
            // Déduire le montant du paiement du solde du compte de l'utilisateur
            try {
                bankAccountService.deductUserBalance(createdPayment.getUser().getCin(), paymentAmount);
            } catch (InsufficientBalanceException e) {
                throw new RuntimeException(e);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }

            // Mettre à jour le solde dans l'objet créé pour refléter le nouveau solde après le paiement
            createdPayment.setBalance(userBalance - paymentAmount);

            // Générer le PDF avec les détails du paiement créé
            ByteArrayOutputStream bos = PDFGeneratorService.generatePaymentPDFReport(createdPayment, userBalance, userBalance - paymentAmount);

            // Télécharger le PDF sur le bureau de l'utilisateur
            try {
                // Obtenir le répertoire du bureau de l'utilisateur
                String desktopPath = System.getProperty("user.home") + "/Desktop/";

                // Nom du fichier PDF
                String fileName = "Payment_" + createdPayment.getPaymentNumber() + ".pdf";

                // Chemin complet du fichier PDF
                String filePath = "C:\\Users\\USER\\Desktop\\mariem\\payment.pdf";

                // Écrire le contenu du fichier PDF dans le répertoire du bureau
                try (FileOutputStream fos = new FileOutputStream(filePath)) {
                    fos.write(bos.toByteArray());
                } catch (IOException e) {
                    throw new RuntimeException("Erreur lors de l'écriture du fichier PDF sur le bureau", e);
                }

                // Retourner le fichier téléchargé dans la réponse
                File file = new File(filePath);
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "attachment; filename=" + fileName);

                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la lecture du fichier PDF", e);
            }
        } else {
            // Retourner une réponse indiquant que le solde est insuffisant
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @PutMapping("/{paymentNumber}")
    public ResponseEntity<Payment> updatePayment(@PathVariable int paymentNumber, @RequestBody @Valid Payment payment) {
        Payment updatedPayment = paymentService.updatePayment(paymentNumber, payment);
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }

    @DeleteMapping("/{paymentNumber}")
    public ResponseEntity<Void> deletePayment(@PathVariable int paymentNumber) {
        paymentService.deletePayment(paymentNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
