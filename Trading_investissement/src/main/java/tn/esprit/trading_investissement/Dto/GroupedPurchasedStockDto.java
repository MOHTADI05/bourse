package tn.esprit.trading_investissement.Dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupedPurchasedStockDto {
    private String company;
    private double averagePurchasePrice;
    private int totalQuantity;
    private double currentPrice;
    private double profitLoss;


}

