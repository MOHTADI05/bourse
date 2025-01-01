package tn.esprit.mfb.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.entity.Amortization;
import tn.esprit.mfb.entity.Payment;
import tn.esprit.mfb.serviceInterface.AmortizationService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("/simulation")
public class SimulationRestController  {

@Autowired
    private AmortizationService amortizationService;
@Autowired
    private ServletContext context;
    @Autowired
    private ServletContext servletContext;
    Amortization a;


    @PostMapping("/amortisation")
    public ResponseEntity<Amortization> retrieveAmortization(@RequestBody Amortization amortization) {
        this.a = amortization;
        amortizationService.initializeUnknownFields(amortization);
        amortization.setPaymentList(amortizationService.calculatePaymentList(amortization.getStartDate(), amortization.getInitialBalance(), amortization.getDurationInMonths(), amortization.getPaymentType(), amortization.getInterestRate(), amortization.getFutureValue()));
        return ResponseEntity.status(200).body(amortization);
    }

   /* @GetMapping("/Scraping")
    public float GetTMMWithJsoup() throws IOException {
        Document doc = Jsoup.connect("http://www.stb.com.tn/fr/site/bourse-change/historique-des-tmm/").get();
        // Utilisez le sélecteur correct pour cibler l'élément souhaité
        String value = doc.select("td.chat-change").last().text(); // ou .html(), selon le cas
        float v = Float.parseFloat(value);
        return v;
    }
*/

    @GetMapping("/CalculateInterest")
    public float CalculateInterest() throws IOException {
        final float marge= (float) 5.27;
        float i = marge + GetTMMWithJsoup();
        return i;
    }

    private float GetTMMWithJsoup() {
        return 0;
    }


    @GetMapping(value = "/createPdf")
    public void createPdf(@ModelAttribute Amortization amortization, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Payment> payments = amortizationService.calculatePaymentList(amortization.getStartDate(), amortization.getInitialBalance(), amortization.getDurationInMonths(), amortization.getPaymentType(), amortization.getInterestRate(), amortization.getFutureValue());
        boolean isFlag = amortizationService.createPdf(payments, (javax.servlet.ServletContext) context, (javax.servlet.http.HttpServletRequest) request, (javax.servlet.http.HttpServletResponse) response);
        if (isFlag) {
            String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "payments" + ".pdf");
            filedownload(fullPath, response, "payments.pdf");
        }
    }

 /*
@GetMapping(value = "/createPdf")
public void createPdf(@ModelAttribute Amortization amortization, HttpServletRequest request, HttpServletResponse response, Model model) {
    List<Payment> payments = amortizationService.calculatePaymentList(amortization.getStartDate(), amortization.getInitialBalance(), amortization.getDurationInMonths(), amortization.getPaymentType(), amortization.getInterestRate(), amortization.getFutureValue());
    boolean isFlag = amortizationService.createPdf(payments, (ServletContext) request.getServletContext(), request, response);
    if (isFlag) {
        String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "payments" + ".pdf");
        filedownload(fullPath, response, "payments.pdf");
    }
}*/


    private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
        File file = new File(fullPath);
        final int BUFFER_SIZE =4096;
        if(file.exists()){
            try{
                FileInputStream inputStream =new FileInputStream(file);
                String mimeType =context.getMimeType(fullPath);
                response.setContentType(mimeType);
                response.setHeader("content-disposition", "attachement; fileName="+ fileName);
                OutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;
                while((bytesRead = inputStream.read(buffer))!= -1){
                    outputStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();
                outputStream.close();
                file.delete();

            } catch (Exception e) {
                e.printStackTrace();

            }
        }


    }




}
