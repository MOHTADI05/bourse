package tn.esprit.mfb.Services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.PartnerRepository;
import tn.esprit.mfb.Repository.ProductRepository;
import tn.esprit.mfb.entity.Partner;
import tn.esprit.mfb.entity.Product;
import tn.esprit.mfb.serviceInterface.IPartnerService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class PartnerService implements IPartnerService {
    private final PartnerRepository partnerRepository;
    private final ProductRepository productrepository ;


    // Méthode pour créer ou mettre à jour un partenaire
    @Override
    public Partner saveOrUpdatePartner(Partner partner) {
        return partnerRepository.save(partner);
    }
    @Override
    // Méthode pour récupérer tous les partenaires
    public List<Partner> getAllPartners() {
        return partnerRepository.findAll();
    }

    // Méthode pour récupérer un partenaire par son ID
    @Override
    public Optional<Partner> getPartnerById(Long partnerId) {
        return partnerRepository.findById(partnerId);
    }

    // Méthode pour supprimer un partenaire par son ID
    @Transactional
    @Override
    public void deletePartnerById(Long partnerId) {
        Optional<Partner> optionalPartner = partnerRepository.findById(partnerId);
        if (optionalPartner.isPresent()) {
            Partner partner = optionalPartner.get();
            // Supprimer les produits associés
            Set<Product> products = partner.getProducts();
            for (Product product : products) {
                // Update or delete the records in the product_credit table that reference this product
                // This will depend on your business logic and the structure of your database
              //  productCreditRepository.deleteByProductId(product.getId());

                product.setPartner(null); // Dissocier le produit du partenaire
                productrepository.delete(product); // Supprimer le produit du repository
            }
            products.clear(); // Effacer la liste des produits associés au partenaire

            // Supprimer le partenaire
            partnerRepository.delete(partner);
        }
    }




}
