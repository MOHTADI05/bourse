package tn.esprit.mfb.Services;


import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.entity.Amortization;
import tn.esprit.mfb.entity.Payment;
import tn.esprit.mfb.serviceInterface.AmortizationService;
import tn.esprit.mfb.serviceInterface.PaymentService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;


@Service
@AllArgsConstructor
public class AmortizationServiceImpl implements AmortizationService {

    private PaymentService paymentService;

    @Override
    public void initializeUnknownFields(Amortization amortization) {
        // extract required parameters
        Date startDate = amortization.getStartDate();
        double initialBalance = amortization.getInitialBalance();
        double interestRate = amortization.getInterestRate();
        int durationInMonths = amortization.getDurationInMonths();
        double futureValue = amortization.getFutureValue();
        int paymentType = amortization.getPaymentType();

        // compute monthly payment
        double monthlyPayment = paymentService.pmt(paymentService.getMonthlyInterestRate(interestRate), durationInMonths, initialBalance, futureValue, paymentType);
        amortization.setMonthlyPayment(monthlyPayment);

        // calculate detailed payment list
        List<Payment> paymentList = calculatePaymentList(startDate, initialBalance, durationInMonths, paymentType, interestRate, futureValue);
        amortization.addAllPayments(paymentList);

    }

    @Override
    public List<Payment> calculatePaymentList(Date startDate, double initialBalance, int durationInMonths,
                                              int paymentType, double interestRate, double futureValue) {
        List<Payment> paymentList = new ArrayList<Payment>();
        Date loopDate = startDate;
        double balance = initialBalance;
        double accumulatedInterest = 0;
        for (int paymentNumber = 1; paymentNumber <= durationInMonths; paymentNumber++)
        {
            if (paymentType == 0)
            {
                loopDate = addOneMonth(loopDate);
            }
            double principalPaid = paymentService.ppmt(paymentService.getMonthlyInterestRate(interestRate), paymentNumber, durationInMonths, initialBalance, futureValue, paymentType);
            double interestPaid = paymentService.ipmt(paymentService.getMonthlyInterestRate(interestRate), paymentNumber, durationInMonths, initialBalance, futureValue, paymentType);
            balance = balance + principalPaid;
            accumulatedInterest += interestPaid;

            Payment payment = new Payment(paymentNumber, loopDate, balance, principalPaid, interestPaid, accumulatedInterest);

            paymentList.add(payment);

            if (paymentType == 1)
            {
                loopDate = addOneMonth(loopDate);
            }
        }
        return paymentList;
    }

    @Override
    public Date addOneMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }


    public boolean createPdf(List<Payment> payments, ServletContext context, HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            String filePath = context.getRealPath("/resources/reports");
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            PdfWriter writer = new PdfWriter(filePath + "/" + "payments" + ".pdf");
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);

            Paragraph paragraph = new Paragraph("Credit monthly payments")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10);
            document.add(paragraph);

            Table table = new Table(UnitValue.createPercentArray(new float[]{2, 2, 2, 2, 2, 2}))
                    .useAllAvailableWidth();

            Stream.of("paymentNumber", "paymentDate", "balance", "principalPaid", "interestPaid", "accumulatedInterest")
                    .forEach(columnTitle -> {
                        Cell header = new Cell()
                                .add(new Paragraph(columnTitle))
                                .setBackgroundColor(new DeviceRgb(169, 169, 169))
                                .setTextAlignment(TextAlignment.CENTER);
                        table.addHeaderCell(header);
                    });

            for (Payment payment : payments) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(payment.getPaymentNumber()))));
                table.addCell(new Cell().add(new Paragraph(payment.getPaymentDate().toString())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(payment.getBalance()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(payment.getPrincipalPaid()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(payment.getInterestPaid()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(payment.getAccumulatedInterest()))));
            }

            document.add(table);
            document.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
