package tn.esprit.mfb.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.mfb.Services.CoursDocService;
import tn.esprit.mfb.entity.CoursDoc;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/doc")
public class CoursDocController {

    private final CoursDocService coursDocService;


    @PostMapping("/upload/{id}")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,@PathVariable Long id) {
        try {
            CoursDoc metadata = coursDocService.storeFile(file,id);
            return ResponseEntity.ok(metadata);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors du téléchargement du fichier : " + e.getMessage());
        }
    }



}
