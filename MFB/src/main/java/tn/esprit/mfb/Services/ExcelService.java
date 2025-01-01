package tn.esprit.mfb.Services;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.TransactionRepository;
import tn.esprit.mfb.entity.Transaction;
import tn.esprit.mfb.serviceInterface.InterfaceExcel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService implements InterfaceExcel {

    @Autowired
    private TransactionRepository transactionRepository;

    public void generateExcelForBankAccount(Long bankAccountId) {
        // Retrieve transactions for the given bank account ID
        List<Transaction> transactions = transactionRepository.findBySourceRIBRib(bankAccountId);

        // Create a new Excel workbook
        Workbook workbook = new HSSFWorkbook(); // Use HSSFWorkbook instead of XSSFWorkbook
        Sheet sheet = workbook.createSheet("Transactions"); // Create a new sheet named "Transactions"

        // Define headers for the Excel sheet
        String[] headers = {"Transaction ID", "Transaction Date", "Amount", "Destination"};
        Row headerRow = sheet.createRow(0); // Create the header row

        // Populate the header row with header labels
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Populate data for each transaction
        int rowNum = 1;
        for (Transaction transaction : transactions) {
            Row row = sheet.createRow(rowNum++);

            // Set values for each cell in the row
            row.createCell(0).setCellValue(transaction.getTransactionId());
            row.createCell(1).setCellValue(transaction.getTransactionDate().toString());
            row.createCell(2).setCellValue(transaction.getAmount());

            // Check if destination is not null before setting its value
            if (transaction.getDestination() != null) {
                row.createCell(3).setCellValue(transaction.getDestination().toString());
            } else {
                row.createCell(3).setCellValue(""); // Or any default value you want to use for null destinations
            }
        }

        // Write the workbook data to a file
        try {
            String filename = "transactions_" + bankAccountId + ".xls"; // Define the filename for the Excel file
            FileOutputStream fileOut = new FileOutputStream(filename); // Create a file output stream
            workbook.write(fileOut); // Write workbook data to the file output stream
            fileOut.close(); // Close the file output stream
            workbook.close(); // Close the workbook
            System.out.println("Excel file has been generated successfully: " + filename); // Print success message
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an IOException occurs
        }
    }
}