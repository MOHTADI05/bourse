package tn.esprit.trading_investissement.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.trading_investissement.Dto.StockDto;
import tn.esprit.trading_investissement.Entities.Stock;
import tn.esprit.trading_investissement.Repositories.PurchasedStockRepository;
import tn.esprit.trading_investissement.Repositories.StockRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PurchasedStockRepository stockCurrentPricesRepository;

    public List<StockDto> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream().map(stock -> {
            double low = stock.getCurrentPrices().stream().min(Double::compareTo).orElse(0.0);
            double high = stock.getCurrentPrices().stream().max(Double::compareTo).orElse(0.0);

            return new StockDto(
                    stock.getId(),
                    stock.getCompany(),
                    stock.getCurrentPrices(),
                    stock.getInitialPrice(),
                    low,
                    high
            );
        }).collect(Collectors.toList());
    }

    public Double getLatestCurrentPrice(Long stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));
        List<Double> prices = stock.getCurrentPrices();
        return (prices != null && !prices.isEmpty()) ? prices.get(prices.size() - 1) : null;
    }

    public Double getLatestCurrentPriceByCompany(String companyName) {
        Stock stock = stockRepository.findByCompany(companyName);
        if (stock != null && !stock.getCurrentPrices().isEmpty()) {
            List<Double> prices = stock.getCurrentPrices();
            return prices.get(prices.size() - 1); // Return the latest price
        }
        return 0.0; // Return 0.0 if no price is found
    }

    // Méthode pour récupérer un stock par son ID
    public Stock getStockById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock with ID " + id + " not found."));
    }




}
