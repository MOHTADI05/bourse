package tn.esprit.mfb.entity;

import java.util.List;

public class ProfitLossData {
    private List<Double> prices;
    private List<String> dates;

    // Constructors, getters, and setters
    public ProfitLossData(List<Double> prices, List<String> dates) {
        this.prices = prices;
        this.dates = dates;
    }

    public List<Double> getPrices() {
        return prices;
    }

    public void setPrices(List<Double> prices) {
        this.prices = prices;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }
}