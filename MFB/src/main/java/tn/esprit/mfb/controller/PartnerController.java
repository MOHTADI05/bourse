package tn.esprit.mfb.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Services.PartnerService;
import tn.esprit.mfb.entity.Partner;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/partners")
@AllArgsConstructor
@CrossOrigin("*")
public class PartnerController {
    private final PartnerService partnerService;


    @GetMapping("/getAll")
    public List<Partner> getAllPartners() {
        return partnerService.getAllPartners();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Partner> getPartnerById(@PathVariable Long id) {
        Optional<Partner> partner = partnerService.getPartnerById(id);
        return partner.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Partner> createPartner(@Valid @RequestBody Partner partner) {
        // Ajouter les contrôles de saisie ici si nécessaire

        Partner savedPartner = partnerService.saveOrUpdatePartner(partner);
        return new ResponseEntity<>(savedPartner, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Partner> updatePartner(@PathVariable Long id, @Valid @RequestBody Partner partner) {
        // Ajouter les contrôles de saisie ici si nécessaire

        if (partnerService.getPartnerById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        partner.setPartnerId(id);
        Partner updatedPartner = partnerService.saveOrUpdatePartner(partner);
        return ResponseEntity.ok(updatedPartner);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        if (!partnerService.getPartnerById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        partnerService.deletePartnerById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /*
    @GetMapping("/{partnerId}/product-requests")
    public ResponseEntity<List<ProductRequest>> getPartnerProductRequests(@PathVariable Long partnerId) {
        Partner partner = partnerService.getPartnerById(partnerId).orElse(null);

        if (partner != null) {
            List<ProductRequest> productRequests = partner.getProductRequests();
            return new ResponseEntity<>(productRequests, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
}
