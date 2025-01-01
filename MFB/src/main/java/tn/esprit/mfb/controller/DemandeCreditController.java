package tn.esprit.mfb.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.entity.Amortissement;
import tn.esprit.mfb.entity.DemandeCredit;
import tn.esprit.mfb.serviceInterface.IDemandeCreditService;
import tn.esprit.mfb.Services.UserExcelExport;
import tn.esprit.mfb.serviceInterface.InterfaceTransaction;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/DemandeCredit")
@AllArgsConstructor
@CrossOrigin("*")

public class DemandeCreditController {
    private final IDemandeCreditService demandeCreditService;
    private  final InterfaceTransaction interfaceTransaction;
    @PostMapping("/create/{id}/{Credit-Id_fund}/{idC}/{credit_idgarant}")
    public ResponseEntity<String> createNewCreditBasedOnExistingCredit(@RequestBody DemandeCredit request, @PathVariable  Long id, @PathVariable("Credit-Id_fund") Long Id_fund, @PathVariable  Long idC, @PathVariable("credit_idgarant") Long Id_garant) {

            DemandeCredit newCredit = demandeCreditService.add (
                    id,
                    Id_fund,
                    idC,
                     Id_garant,
                    request.getYear(),
                    request.getTypeCredit(),
                    request.getAmount(),
                    request.getMonthlyPaymentDate()

            );
            //return newCredit;
        if(newCredit == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("vous pas client");
        }else{
            return  ResponseEntity.ok(newCredit.getReason());
        }

    }
    @PutMapping("/update/{id}/{Credit-Id_fund}/{idC}/{credit_idgarant}")
    public DemandeCredit updateDemandeCredit(@RequestBody DemandeCredit request,@PathVariable  Long id,  @PathVariable("Credit-Id_fund") Long Id_fund,@PathVariable  Long idC, @PathVariable("credit_idgarant") Long Id_garant) {

        DemandeCredit newCredit = demandeCreditService.add (
                id,
                Id_fund,
                idC,
                Id_garant,
                request.getYear(),
                request.getTypeCredit(),
                request.getAmount(),
                request.getMonthlyPaymentDate()

        );
        return newCredit;

    }
    @DeleteMapping("/removecredit/{credit-id}")
    @ResponseBody
    public void removecredit(@PathVariable("credit-id") Long creditId) {
        demandeCreditService.DeleteCredit(creditId);
    }


    @GetMapping("/simulate/{mnttotl}/{period}")
    @ResponseBody
    public Amortissement simulate(@PathVariable("mnttotl") float mnttotl, @PathVariable("period") float period) {
        DemandeCredit cr=new DemandeCredit(mnttotl,period);

        return demandeCreditService.Simulateur(cr);

    }

    @GetMapping("/export/excel/{mnttotl}/{period}")
    @ResponseBody
    public void exportToExcel(HttpServletResponse response, @PathVariable("mnttotl") float mnttotl, @PathVariable("period") float period
            ) throws IOException {

        DemandeCredit cr=new DemandeCredit(mnttotl,period);

        float Interest;
        if (cr.getYear() > 5) {
            Interest = 14; // Si la durée est supérieure à 5 ans, définissez le taux d'intérêt à 4%
        } else {
            Interest = 12; // Sinon, définissez le taux d'intérêt à 12%
        }
        cr.setInterest(Interest);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";

        String headervalue = "attachment; filename=Tableau_Credit_N_"+cr.getIdDemandecredit()+".xlsx";
        response.setHeader(headerKey, headervalue);
        Amortissement[] credit = demandeCreditService.TabAmortissement(cr);
        List<Amortissement> list = Arrays.asList(credit);
        UserExcelExport exp =new UserExcelExport(list);
        //UserExcelExporter exp = new UserExcelExporter(list);
        exp.export(response);

    }
    @PostMapping("/pay/{idC}/{fundId}/{creditId}")
    public void payNextAmortization(@PathVariable("idC") long sourceAccountDTO,
                                    @PathVariable("fundId") Long fundId,
                                    @PathVariable("creditId") Long creditId) {
        DemandeCredit credit = demandeCreditService.findById(creditId);
        interfaceTransaction.transferMoneyToFund(sourceAccountDTO, fundId, credit);
    }

    @GetMapping("/montantRestantParAnnee/{idC}")
    public ResponseEntity<Map<Integer, Float>> getMontantRestantParAnnee(@PathVariable("idC") Long idc) {

        // Supposons que vous ayez une méthode pour récupérer la liste d'amortissements depuis votre base de données
        List<Amortissement> amortissementList = demandeCreditService.getAllAmortissementsByUser(idc);

        // Utilisez la méthode du service pour obtenir les données nécessaires pour la charte
        Map<Integer, Float> montantRestantParAnnee = demandeCreditService.getMontantRestantParAnnee(amortissementList);

        // Retournez les données JSON
        return new ResponseEntity<>(montantRestantParAnnee, HttpStatus.OK);
    }
    @GetMapping("/findbyclient/{idClient}")
    public List<DemandeCredit> getDemandesCreditParClient(@PathVariable("idClient") Long idClient) {

        return demandeCreditService.findAllCreditByClient(idClient);
    }
}
