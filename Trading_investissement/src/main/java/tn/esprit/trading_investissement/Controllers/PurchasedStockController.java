package tn.esprit.trading_investissement.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.trading_investissement.Dto.GroupedPurchasedStockDto;
import tn.esprit.trading_investissement.Entities.PurchasedStock;
import tn.esprit.trading_investissement.Entities.User;
import tn.esprit.trading_investissement.Repositories.PurchasedStockRepository;
import tn.esprit.trading_investissement.Repositories.UserRepository;
import tn.esprit.trading_investissement.Services.PurchasedStockService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/purchased-stocks")
public class PurchasedStockController {
    @Autowired
    private PurchasedStockService purchasedStockService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PurchasedStockRepository purchasedStockRepository;

//    @GetMapping
//    public List<PurchasedStock> getAllPurchasedStocks() {
//        return purchasedStockService.getAllPurchasedStocks();
//    }
@GetMapping("/user/{userId}")
public List<PurchasedStock> getPurchasedStocksByUser(@PathVariable Long userId) {
    return purchasedStockService.getAllPurchasedStocksByUser(userId);
}

//    @PostMapping
//    public PurchasedStock addPurchasedStock(@RequestBody PurchasedStock purchasedStock) {
//        return purchasedStockService.savePurchasedStock(purchasedStock);
//    }

    @GetMapping("/grouped")
    public List<GroupedPurchasedStockDto> getGroupedPurchasedStocks() {
        return purchasedStockService.getGroupedPurchasedStocks();
    }

    @PostMapping
    public PurchasedStock createPurchasedStock(@RequestBody Map<String, Object> payload) {
        // Récupérer l'utilisateur à partir de l'ID
        Long userId = Long.valueOf(payload.get("userId").toString());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Créer un nouvel objet PurchasedStock
        PurchasedStock purchasedStock = new PurchasedStock();
        purchasedStock.setCompany(payload.get("company").toString());
        purchasedStock.setPrice(Double.valueOf(payload.get("price").toString()));
        purchasedStock.setQuantity(Integer.valueOf(payload.get("quantity").toString()));

        // Associer l'utilisateur au PurchasedStock
        purchasedStock.setUser(user);

        // Sauvegarder dans la base de données
        return purchasedStockRepository.save(purchasedStock);
    }


    @GetMapping("/grouped/user/{userId}")
    public ResponseEntity<List<GroupedPurchasedStockDto>> getGroupedPurchasedStocksByUser(@PathVariable Long userId) {
        try {
            List<GroupedPurchasedStockDto> groupedStocks = purchasedStockService.getGroupedPurchasedStocksByUser(userId);
            return ResponseEntity.ok(groupedStocks); // Retourner un code 200 avec les données
        } catch (Exception e) {
            // Gérer les exceptions et retourner un message d'erreur approprié
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList()); // Retourner une liste vide en cas d'erreur
        }
    }




//    @GetMapping("/user/{userId}")
//    public List<PurchasedStock> getPurchasedStocksByUser2(@PathVariable Long userId) {
//        return purchasedStockService.getAllPurchasedStocksByUser(userId);
//    }



}

