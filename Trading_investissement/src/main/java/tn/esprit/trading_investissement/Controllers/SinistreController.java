package tn.esprit.trading_investissement.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.trading_investissement.Entities.SinistreType;
import tn.esprit.trading_investissement.Services.SinistreService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sinistre")
public class SinistreController {

    private final SinistreService sinistreService;

    public SinistreController(SinistreService sinistreService) {
        this.sinistreService = sinistreService;
    }

    @PostMapping("/appliquer")
    public ResponseEntity<Map<String, String>> appliquerSinistre(@RequestParam SinistreType sinistreType) {
        sinistreService.appliquerSinistre(sinistreType);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Sinistre appliqué avec succès : " + sinistreType);
        response.put("type", sinistreType.name());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/types")
    public ResponseEntity<SinistreType[]> getSinistreTypes() {
        return ResponseEntity.ok(SinistreType.values());
    }
}



