package tn.esprit.mfb.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.SetupIntentConfirmParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.mfb.Repository.UserRepository;
import tn.esprit.mfb.Services.*;
import tn.esprit.mfb.entity.Payment;
import tn.esprit.mfb.entity.User;
import tn.esprit.mfb.entity.Product;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;



@RestController
@RequestMapping("/products")
@CrossOrigin("*")
@AllArgsConstructor
public class ProductController {
    private PaymentServiceImpl paymentService;
    private final StripeService stripeService; // Service pour récupérer la clé d'API Stripe

    private final ProductService productService;
    private final UserRepository clientService;
    private final MinioService minioService;
    private EmailService emsender;
    @GetMapping("/get")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/images/{fileName}")
    public ResponseEntity<byte[]> getFile( @PathVariable String fileName) {
        try {
            // Retrieve file from Minio
            InputStream fileStream = minioService.downloadFile("images", fileName);

            // Convert InputStream to byte array
            byte[] bytes = fileStream.readAllBytes();

            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Change to appropriate content type

            // Return the file as a ResponseEntity
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<Product> createOrUpdateProduct(
            @Valid @ModelAttribute Product product,
            @RequestParam("image") MultipartFile image) {
        // Save product details
        productService.saveOrUpdateProduct(product);

        // Save image file
        if (image != null && !image.isEmpty()) {
            try {
                // Process and save the image file
                byte[] imageData = image.getBytes();
                String fileName = image.getOriginalFilename();
                product.setFilename(fileName);
                productService.saveOrUpdateProduct(product);
                minioService.uploadFile("images", imageData, fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        // Ajouter les contrôles de saisie ici si nécessaire

        if (productService.getProductById(id) == null) {
            return ResponseEntity.notFound().build();
        }


        product.setProductId(id);
        Product updatedProduct = productService.saveOrUpdateProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }
    //MODIFICATION PRODUIT
    @PutMapping("/updateProduct/{IdPartner}")
    @ResponseBody
    public Product updateProduct(@RequestBody Product p,@PathVariable("IdPartner") Long id) {
        Product prd=productService.updateProduct(p, id);
        return prd;

    }
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/PDF")
    public ResponseEntity<InputStreamResource> employeeReport() throws IOException {
        List<Product> p = (List<Product>) productService.getAllProducts();

        ByteArrayOutputStream bis = PDFGeneratorService.employeePDFReport(p);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=Products.pdf");

        // return ResponseEntity.ok().headers(headers)
        //         .body(new InputStreamResource(bis));

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new ByteArrayInputStream(bis.toByteArray())));
    }
    //@GetMapping(value = "/PDF")
//public ResponseEntity<byte[]> employeeReport() throws IOException {
//    List<Product> p = (List<Product>) productService.getAllProducts();
//
//    ByteArrayOutputStream bos = PDFGeneratorService.employeePDFReport(p);
//    HttpHeaders headers = new HttpHeaders();
//    headers.add("Content-Disposition", "inline; filename=Products.pdf");
//
//    return ResponseEntity.ok()
//            .headers(headers)
//            .contentType(MediaType.APPLICATION_PDF)
//            .body(bos.toByteArray());
//}
    /*
@GetMapping(value = "/PDF")
public ResponseEntity<InputStreamResource> employeeReport() throws IOException {
    List<Product> p = (List<Product>) productService.getAllProducts();

    ByteArrayOutputStream bis = PDFGeneratorService.employeePDFReport(p);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=Products.pdf");

   //  return ResponseEntity.ok().headers(headers).body(new InputStreamResource(bis));

    return ResponseEntity.ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(new ByteArrayInputStream(bis.toByteArray())));
}*/
    @PostMapping("/buy/{clientId}/{productId}/{periode}")
    public ResponseEntity<String> buyProduct(@PathVariable Long clientId, @PathVariable Long productId, @PathVariable int periode) throws StripeException {
        User client = clientService.findByCin(clientId);
        Product product = productService.getProductById(productId);
        boolean canAfford = productService.acheter(client, product, periode);
        if (canAfford) {
            String message = "Bonjour " + client.getNom() + ",\n\n" +
                    "Vous avez acheté le produit : " + product.getProductName() + "\n" +
                    "Valeur du produit : " + product.getValueEXC() + "\n\n" +
                    "Cordialement,\n" +
                    "Votre équipe de vente";
            emsender.sendSimpleEmail(client.getEmail(), "Confirmation d'achat", message);
            int nbr = (int)periode/12;

            double balance = client.getNetIncome();
            double principalPaid = product.getValueEXC();
            double interestPaid = principalPaid * nbr * 0.03;
            double accumulatedInterest = principalPaid * nbr * 0.05; // Calculate accumulated interest


            Payment payment = new Payment();
            payment.setPaymentDate(new Date()); // Set payment date as current date
            payment.setBalance(balance);
            payment.setPrincipalPaid(principalPaid);
            payment.setInterestPaid(interestPaid);
            payment.setAccumulatedInterest(accumulatedInterest);
            //    payment.setClient(client);


            String apiKey = stripeService.getApiKey();
            Stripe.apiKey = apiKey;
            long amountInCents = (long) (payment.getPrincipalPaid() * 100);
            Payment createdPayment = paymentService.createPayment(payment);


            SessionCreateParams params = SessionCreateParams.builder()
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(String.valueOf(SetupIntentConfirmParams.PaymentMethodOptions.AcssDebit.Currency.USD))
                                                    .setUnitAmount(amountInCents) // 10.00 USD
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(product.getProductName())
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .setQuantity(1L)
                                    .build()
                    )
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:4200/front/confirm-payment/"+createdPayment.getPaymentNumber())
                    .setCancelUrl("http://localhost:4200/front/confirm-payment/")
                    .build();

            Session session = Session.create(params);

            String paymentLink = "{\"paymentLink\": \"" + session.getUrl() + "\"}";

            // Return JSON response
            return ResponseEntity.ok(paymentLink);







/*
            // Créer un paramètre pour l'intent de paiement
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency("eur") // ou toute autre devise nécessaire
                    .build();

            try {
                PaymentIntent intent = PaymentIntent.create(params);

                Payment createdPayment = paymentService.createPayment(payment);

                // Préparer la réponse avec l'ID de l'intent de paiement pour le frontend
                Map<String, Object> response = new HashMap<>();
                response.put("paymentIntentId", intent.getId());
                response.put("clientSecret", intent.getClientSecret()); // Important pour finaliser le paiement côté client

                return ResponseEntity.ok().body(response);
            } catch (StripeException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            } */




            // return new ResponseEntity<>( HttpStatus.OK);

            //
        } else {
            return new ResponseEntity<>( HttpStatus.NOT_ACCEPTABLE);
        }
    }


}
