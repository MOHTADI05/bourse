package tn.esprit.trading_investissement.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StockDto {
    private Long id;
    private String company;
    private List<Double> currentPrices;
    private Double initialPrice;
    private Double low; // Valeur minimale
    private Double high; // Valeur maximale
}
