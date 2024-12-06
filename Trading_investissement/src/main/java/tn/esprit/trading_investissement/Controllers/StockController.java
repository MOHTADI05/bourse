package tn.esprit.trading_investissement.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.trading_investissement.Dto.StockDto;
import tn.esprit.trading_investissement.Entities.Stock;
import tn.esprit.trading_investissement.Services.StockService;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public List<StockDto> getStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/{stockId}/latest-price")
    public ResponseEntity<Double> getLatestCurrentPrice(@PathVariable Long stockId) {
        Double latestPrice = stockService.getLatestCurrentPrice(stockId);
        return ResponseEntity.ok(latestPrice != null ? latestPrice : 0.0);
    }

    @GetMapping("/{id}")
    public Stock getStockById(@PathVariable Long id) {
        return stockService.getStockById(id);
    }


}

