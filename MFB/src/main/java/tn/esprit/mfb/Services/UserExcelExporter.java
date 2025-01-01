package tn.esprit.mfb.Services;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.entity.Payment;

import java.io.IOException;
import java.util.List;

@Service
public class UserExcelExporter {

    private List<Payment> paymentList;

    public UserExcelExporter(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public void export(HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Payments");

        createHeaderRow(sheet);
        writeDataRows(sheet);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=payments.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);

        String[] headers = {"Payment Number", "Payment Date", "Balance", "Principal Paid", "Interest Paid", "Accumulated Interest"};
        CellStyle headerCellStyle = sheet.getWorkbook().createCellStyle();
        Font headerFont = sheet.getWorkbook().createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    private void writeDataRows(Sheet sheet) {
        int rowNum = 1;
        for (Payment payment : paymentList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(payment.getPaymentNumber());
            row.createCell(1).setCellValue(payment.getPaymentDate().toString());
            row.createCell(2).setCellValue(payment.getBalance());
            row.createCell(3).setCellValue(payment.getPrincipalPaid());
            row.createCell(4).setCellValue(payment.getInterestPaid());
            row.createCell(5).setCellValue(payment.getAccumulatedInterest());
        }
    }
}