package tn.esprit.mfb.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Services.demande_service;
import tn.esprit.mfb.entity.Demand;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/demande")
public class demande_ressource {
    private final demande_service Demande_service;

    public demande_ressource(demande_service demandeService) {
        Demande_service = demandeService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Demand>> getAllFavorite() {
        List<Demand> FAV = Demande_service.findAllimmobilier();
        return new ResponseEntity<>(FAV, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Demand> getFavoriteById(@PathVariable("id") Long id) {
        Demand INV = Demande_service.findimmobilierById(id);
        return new ResponseEntity<>(INV, HttpStatus.OK);

    }
    @GetMapping("/{immobilierId}")
    public ResponseEntity<List<Demand>> getDemandsForImmobilier(@PathVariable Long immobilierId) {
        List<Demand> demands = Demande_service.getDemandsForImmobilier(immobilierId);
        return ResponseEntity.ok(demands);
    }
}
