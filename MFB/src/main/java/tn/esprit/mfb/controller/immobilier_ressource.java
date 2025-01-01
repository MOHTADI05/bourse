package tn.esprit.mfb.controller;

import tn.esprit.mfb.Services.immobilier_service;
import tn.esprit.mfb.entity.immobilier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/immobilier")
public class immobilier_ressource {
    private final immobilier_service Immobilier_service;

    public immobilier_ressource(immobilier_service immobilierService) {
        Immobilier_service = immobilierService;
    }


    @GetMapping("/all")
        public ResponseEntity<List<immobilier>> getAllFavorite() {
            List<immobilier> FAV = Immobilier_service.findAllimmobilier();
            return new ResponseEntity<>(FAV, HttpStatus.OK);
        }

        @GetMapping("/find/{INV_id}")
        public ResponseEntity<immobilier> getFavoriteById(@PathVariable("INV_id") Long id) {
            immobilier INV = Immobilier_service.findimmobilierById(id);
            return new ResponseEntity<>(INV, HttpStatus.OK);

        }

        @PostMapping("/add")
        public ResponseEntity<immobilier> addFavorite(@RequestBody immobilier FAV) {
            immobilier newINV = Immobilier_service.addimmobilier(FAV);
            return new ResponseEntity<>(newINV, HttpStatus.CREATED);

        }

        @PutMapping("/update")
        public ResponseEntity<immobilier> updateFavorite(@RequestBody immobilier FAV) {
            immobilier updateINV = Immobilier_service.updatefavorite(FAV);
            return new ResponseEntity<>(updateINV, HttpStatus.OK);

        }

        @DeleteMapping("/delete/{INV_id}")
        public ResponseEntity<?> deleteFavorite(@PathVariable("INV_id") Long id) {
            Immobilier_service.deletefimmobilier(id);
            return new ResponseEntity<>(HttpStatus.OK);

        }
    }


