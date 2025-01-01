package tn.esprit.mfb.Services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.PartnerRepository;
import tn.esprit.mfb.Repository.ProductRepository;
import tn.esprit.mfb.entity.Partner;
import tn.esprit.mfb.entity.Product;
import tn.esprit.mfb.entity.User;
import tn.esprit.mfb.serviceInterface.IProductService;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {
    private ProductRepository productRepository;
   private PartnerRepository partnerRepository;
    private EmailSenderService emsender;
@Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
@Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }
    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
    @Override
    public Product saveOrUpdateProduct(Product product) {
        if (product.getProductId() != null) {
            // Logique de mise à jour pour un produit existant
            return productRepository.findById(product.getProductId()).map(c -> {
                c.setIsAvailable("Available"); // Met à jour le statut si le produit existe
                // Copiez ici toute autre mise à jour de champ que vous souhaitez appliquer
                return productRepository.save(c);
            }).orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID : " + product.getProductId()));
        } else {
            // Logique pour un nouveau produit
            product.setIsAvailable("Available"); // Initialise le statut pour un nouveau produit
            // Initialiser ici tout autre champ nécessaire pour un nouveau produit
            return productRepository.save(product);
        }
    }

    @Override
    public Product updateProduct(Product prod, Long partnerid) {
        Partner pr =partnerRepository.findById(partnerid).get();
        prod.setPartner(pr);
        return  productRepository.save(prod)	;



    }

   /* public List<Pack> getProductCredits(Long productId) {
        Product product = getProductById(productId);
        return product.getpacks();
    }*/

    //Les ajustements de prix sont basés sur des pourcentages spécifiques en fonction des valeurs initiales des produits.
    @Scheduled(cron = "* * * 05 * *" ) //sera exécutée à chaque seconde de l'heure 5 de chaque jour
    public void exclusivitePrix() {
        List<Product> pr=(List)(productRepository.findAll());
        for (Iterator iterator = pr.iterator(); iterator.hasNext();) {
            Product product = (Product) iterator.next();
            if(product.getValueProduct()<100) {
                product.setValueEXC(product.getValueProduct()*90/100);
                productRepository.save(product);}
            else if ((product.getValueProduct()<1000) && (product.getValueProduct()>100)) {
                product.setValueEXC(product.getValueProduct()*92/100);
                productRepository.save(product);}
            else {
                product.setValueEXC(product.getValueProduct()*94/100);
                productRepository.save(product);
            }
        }
    }

    @Override
    @Transactional
    public boolean acheter(User client, Product product, int periode) {
        double monthlyPayment;
       if(Objects.equals(product.getIsAvailable(), "Available"))
       {


            double netIncome = client.getNetIncome(); // Assurez-vous que la classe Client a une méthode getNetIncome
            monthlyPayment=product.getValueEXC()/periode;
            double affordabilityIndex = netIncome / monthlyPayment;
            boolean canAfford = affordabilityIndex >= 1.2;
            if (canAfford) {
                System.out.println("achat avec succe");

                product.setIsAvailable("Not Available");
                client.setProduct(product);
             //   String message = "Bonjour " + client.getClientName() + ", vous avez acheté le produit " + product.getProductName() + " avec une valeur de " + product.getValueEXC();
             //   emsender.sendSimpleEmail(client.getClientEmail(), "Confirmation d'achat", message);


            }
           return canAfford;

        } else {
            // Print a message
            System.out.println("Product not available");
            return false;
        }
    }


}