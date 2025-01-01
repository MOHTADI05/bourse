package tn.esprit.mfb.Services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.entity.Payment;
import tn.esprit.mfb.entity.Product;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PDFGeneratorService {




    private static Logger logger = LoggerFactory.getLogger(PDFGeneratorService.class);
    public static ByteArrayOutputStream employeePDFReport(List<Product> products) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Ajoutez le texte au fichier PDF
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph("Tableau des produits", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            // Ajoutez les détails des produits au PDF
            PdfPTable table = new PdfPTable(4);
            Stream.of("Id", "Nom Produit", "Prix Produit", "Nom Partenaire").forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(headerTitle, headFont));
                table.addCell(header);
            });

            for (Product p : products) {
                PdfPCell idCell = new PdfPCell(new Phrase(String.valueOf(p.getProductId())));
                idCell.setPaddingLeft(4);
                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idCell);

                PdfPCell nameCell = new PdfPCell(new Phrase(p.getProductName()));
                nameCell.setPaddingLeft(4);
                nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(nameCell);

                PdfPCell priceCell = new PdfPCell(new Phrase(String.valueOf(p.getValueProduct())));
                priceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                priceCell.setPaddingRight(4);
                table.addCell(priceCell);

                PdfPCell partnerCell = new PdfPCell(new Phrase(p.getPartner().getPartnerName()));
                partnerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                partnerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                partnerCell.setPaddingRight(4);
                table.addCell(partnerCell);
            }
            document.add(table);

            document.close();
        } catch (DocumentException e) {
            logger.error
                    (e.toString());
        }

        return out;
    }


    public static ByteArrayOutputStream generatePaymentPDFReport(Payment payment, Double initialBalance, Double finalBalance) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Ajouter le texte au fichier PDF
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph("Détails du paiement", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            // Ajouter les détails du paiement au PDF
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(80);
            table.setWidths(new int[]{1, 3});

            addTableCell(table, "Numéro de paiement:", String.valueOf(payment.getPaymentNumber()));
            addTableCell(table, "Date du paiement:", payment.getPaymentDate().toString());
            addTableCell(table, "Montant initial du solde:", String.valueOf(initialBalance));
            addTableCell(table, "Montant du paiement:", String.valueOf(payment.getPrincipalPaid()));
            addTableCell(table, "Montant final du solde:", String.valueOf(finalBalance));

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            logger.error(e.toString());
        }

        return out;
    }

    private static void addTableCell(PdfPTable table, String title, String value) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        PdfPCell cell = new PdfPCell(new Phrase(title, font));
        cell.setBorderWidth(0);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(value));
        cell.setBorderWidth(0);
        table.addCell(cell);
    }

}
