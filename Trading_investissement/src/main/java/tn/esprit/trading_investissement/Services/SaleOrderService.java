package tn.esprit.trading_investissement.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.trading_investissement.Entities.PurchasedStock;
import tn.esprit.trading_investissement.Entities.SaleOrder;
import tn.esprit.trading_investissement.Entities.Stock;
import tn.esprit.trading_investissement.Repositories.PurchasedStockRepository;
import tn.esprit.trading_investissement.Repositories.SaleOrderRepository;
import tn.esprit.trading_investissement.Repositories.StockRepository;

import java.util.List;

@Service
public class SaleOrderService {
    @Autowired
    private SaleOrderRepository saleOrderRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private PurchasedStockRepository purchasedStockRepository;

//    public SaleOrder createSaleOrder(SaleOrder saleOrder) {
//        return saleOrderRepository.save(saleOrder);
//    }

    public List<SaleOrder> getAllSaleOrders() {
        return saleOrderRepository.findAll();
    }
    public SaleOrder createSaleOrder(SaleOrder saleOrder) {
        // Récupérer le stock correspondant
        Stock stock = stockRepository.findByCompany(saleOrder.getCompanyName());
        if (stock == null || stock.getCurrentPrices().isEmpty()) {
            throw new IllegalArgumentException("Stock not found or no current price available.");
        }

        // Récupérer tous les stocks correspondant à l'entreprise
        List<PurchasedStock> purchasedStocks = purchasedStockRepository.findByCompany(saleOrder.getCompanyName());
        if (purchasedStocks.isEmpty()) {
            throw new IllegalArgumentException("No purchased stocks found for the company.");
        }

        // Gérer les quantités
        int remainingQuantityToSell = saleOrder.getSaleQuantity();
        for (PurchasedStock purchasedStock : purchasedStocks) {
            if (remainingQuantityToSell == 0) break;

            if (purchasedStock.getQuantity() >= remainingQuantityToSell) {
                purchasedStock.setQuantity(purchasedStock.getQuantity() - remainingQuantityToSell);
                purchasedStockRepository.save(purchasedStock);
                remainingQuantityToSell = 0;
            } else {
                remainingQuantityToSell -= purchasedStock.getQuantity();
                purchasedStock.setQuantity(0);
                purchasedStockRepository.save(purchasedStock);
            }
        }

        if (remainingQuantityToSell > 0) {
            throw new IllegalArgumentException("Not enough quantity available to sell.");
        }

        // Sauvegarder la commande de vente
        return saleOrderRepository.save(saleOrder);
    }


    public void deleteSaleOrder(Long id) {
        SaleOrder saleOrder = saleOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sale order not found"));

        // Rétablir la quantité dans PurchasedStock
        PurchasedStock purchasedStock = purchasedStockRepository.findByCompany(saleOrder.getCompanyName())
                .stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Purchased stock not found"));
        purchasedStock.setQuantity(purchasedStock.getQuantity() + saleOrder.getSaleQuantity());
        purchasedStockRepository.save(purchasedStock);

        // Supprimer la commande de vente
        saleOrderRepository.deleteById(id);
    }


    public List<SaleOrder> getSaleOrdersByCompany(String companyName) {
        return saleOrderRepository.findByCompanyName(companyName);
    }


    public List<SaleOrder> getSaleOrdersByUser(Long userId) {
        return saleOrderRepository.findByUserId(userId);
    }

    public List<SaleOrder> getSaleOrdersByCompanyAndUser(String companyName, Long userId) {
        return saleOrderRepository.findByCompanyNameAndUserId(companyName, userId);
    }






}
