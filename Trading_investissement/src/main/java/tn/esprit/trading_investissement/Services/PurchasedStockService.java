package tn.esprit.trading_investissement.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.trading_investissement.Dto.GroupedPurchasedStockDto;
import tn.esprit.trading_investissement.Entities.PurchasedStock;
import tn.esprit.trading_investissement.Entities.Stock;
import tn.esprit.trading_investissement.Repositories.PurchasedStockRepository;
import tn.esprit.trading_investissement.Repositories.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PurchasedStockService {
    @Autowired
    private PurchasedStockRepository purchasedStockRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService; // To fetch the latest stock prices



    public List<PurchasedStock> getAllPurchasedStocksByUser(Long userId) {
        // Récupérer uniquement les PurchasedStock liés à l'utilisateur spécifié
        List<PurchasedStock> purchasedStocks = purchasedStockRepository.findByUserId(userId);

        purchasedStocks.forEach(purchasedStock -> {
            Stock stock = stockRepository.findByCompany(purchasedStock.getCompany());
            if (stock != null && !stock.getCurrentPrices().isEmpty()) {
                // Mettre à jour le prix actuel basé sur la liste des prix
                purchasedStock.setCurrentPrice(stock.getCurrentPrices().get(stock.getCurrentPrices().size() - 1));
            } else {
                purchasedStock.setCurrentPrice(null); // Ou gérer comme "N/A"
            }
        });

        return purchasedStocks;
    }


    public PurchasedStock savePurchasedStock(PurchasedStock purchasedStock) {
        return purchasedStockRepository.save(purchasedStock);
    }

    public List<GroupedPurchasedStockDto> getGroupedPurchasedStocks() {
        List<PurchasedStock> purchasedStocks = purchasedStockRepository.findAll();

        // Group purchased stocks by company
        Map<String, List<PurchasedStock>> groupedByCompany = purchasedStocks.stream()
                .collect(Collectors.groupingBy(PurchasedStock::getCompany));

        List<GroupedPurchasedStockDto> groupedStocks = new ArrayList<>();

        for (Map.Entry<String, List<PurchasedStock>> entry : groupedByCompany.entrySet()) {
            String company = entry.getKey();
            List<PurchasedStock> stocks = entry.getValue();

            // Calculate total quantity and average purchase price
            int totalQuantity = stocks.stream().mapToInt(PurchasedStock::getQuantity).sum();
            double averagePurchasePrice = stocks.stream()
                    .mapToDouble(stock -> stock.getPrice() * stock.getQuantity())
                    .sum() / totalQuantity;

            // Get the latest current price for the company
            double currentPrice = stockService.getLatestCurrentPriceByCompany(company);

            // Calculate profit or loss
            double profitLoss = (currentPrice - averagePurchasePrice) * totalQuantity;

            // Add the grouped stock data to the DTO list
            groupedStocks.add(new GroupedPurchasedStockDto(company, averagePurchasePrice, totalQuantity, currentPrice, profitLoss));
        }

        return groupedStocks;
    }




    public List<GroupedPurchasedStockDto> getGroupedPurchasedStocksByUser2(Long userId) {
        // Récupérer uniquement les PurchasedStock liés à l'utilisateur spécifié
        List<PurchasedStock> purchasedStocks = purchasedStockRepository.findByUserId(userId);

        // Vérifier si les stocks existent
        if (purchasedStocks.isEmpty()) {
            return new ArrayList<>(); // Retourner une liste vide si aucun stock n'est trouvé
        }

        // Mettre à jour le prix actuel pour chaque stock
        purchasedStocks.forEach(purchasedStock -> {
            Stock stock = stockRepository.findByCompany(purchasedStock.getCompany());
            if (stock != null && !stock.getCurrentPrices().isEmpty()) {
                purchasedStock.setCurrentPrice(stock.getCurrentPrices().get(stock.getCurrentPrices().size() - 1));
            } else {
                purchasedStock.setCurrentPrice(null); // Gérer comme "N/A"
            }
        });

        // Grouper les PurchasedStocks par entreprise
        Map<String, List<PurchasedStock>> groupedByCompany = purchasedStocks.stream()
                .collect(Collectors.groupingBy(PurchasedStock::getCompany));

        List<GroupedPurchasedStockDto> groupedStocks = new ArrayList<>();

        // Calculer les données agrégées pour chaque groupe
        for (Map.Entry<String, List<PurchasedStock>> entry : groupedByCompany.entrySet()) {
            String company = entry.getKey();
            List<PurchasedStock> stocks = entry.getValue();

            // Calculer la quantité totale et le prix moyen d'achat
            int totalQuantity = stocks.stream().mapToInt(PurchasedStock::getQuantity).sum();
            double averagePurchasePrice = stocks.stream()
                    .mapToDouble(stock -> stock.getPrice() * stock.getQuantity())
                    .sum() / totalQuantity;

            // Obtenir le dernier prix actuel pour l'entreprise
            double currentPrice = stockService.getLatestCurrentPriceByCompany(company);

            // Calculer le profit ou la perte
            double profitLoss = (currentPrice - averagePurchasePrice) * totalQuantity;

            // Ajouter les données groupées au DTO
            groupedStocks.add(new GroupedPurchasedStockDto(company, averagePurchasePrice, totalQuantity, currentPrice, profitLoss));
        }

        return groupedStocks;
    }




    public List<GroupedPurchasedStockDto> getGroupedPurchasedStocksByUser(Long userId) {
        // Récupérer uniquement les PurchasedStock liés à l'utilisateur spécifié
        List<PurchasedStock> purchasedStocks = purchasedStockRepository.findByUserId(userId);

        // Vérifier si des stocks ont été trouvés
        if (purchasedStocks.isEmpty()) {
            return new ArrayList<>(); // Retourner une liste vide si aucun stock n'est trouvé
        }

        // Mettre à jour les prix actuels pour chaque stock
        purchasedStocks.forEach(purchasedStock -> {
            Stock stock = stockRepository.findByCompany(purchasedStock.getCompany());
            if (stock != null && !stock.getCurrentPrices().isEmpty()) {
                purchasedStock.setCurrentPrice(stock.getCurrentPrices().get(stock.getCurrentPrices().size() - 1));
            } else {
                purchasedStock.setCurrentPrice(null); // Ou gérer comme "N/A"
            }
        });

        // Grouper les PurchasedStocks par entreprise
        Map<String, List<PurchasedStock>> groupedByCompany = purchasedStocks.stream()
                .collect(Collectors.groupingBy(PurchasedStock::getCompany));

        List<GroupedPurchasedStockDto> groupedStocks = new ArrayList<>();

        // Calculer les données agrégées pour chaque groupe
        for (Map.Entry<String, List<PurchasedStock>> entry : groupedByCompany.entrySet()) {
            String company = entry.getKey();
            List<PurchasedStock> stocks = entry.getValue();

            // Calculer la quantité totale et le prix moyen d'achat
            int totalQuantity = stocks.stream().mapToInt(PurchasedStock::getQuantity).sum();
            double averagePurchasePrice = stocks.stream()
                    .mapToDouble(stock -> stock.getPrice() * stock.getQuantity())
                    .sum() / totalQuantity;

            // Obtenir le dernier prix actuel pour l'entreprise
            double currentPrice = stockService.getLatestCurrentPriceByCompany(company);

            // Calculer le profit ou la perte
            double profitLoss = (currentPrice - averagePurchasePrice) * totalQuantity;

            // Ajouter les données groupées au DTO
            groupedStocks.add(new GroupedPurchasedStockDto(company, averagePurchasePrice, totalQuantity, currentPrice, profitLoss));
        }

        return groupedStocks;
    }




}


