package tn.esprit.trading_investissement.Services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.trading_investissement.Entities.SinistreType;
import tn.esprit.trading_investissement.Entities.Stock;
import tn.esprit.trading_investissement.Repositories.StockRepository;

import java.util.List;
import java.util.Random;

@Service
public class SinistreService {

    private final StockRepository stockRepository;

    public SinistreService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void appliquerSinistre(SinistreType sinistreType) {
        List<Stock> stocks = stockRepository.findAll();
        Random random = new Random();

        for (Stock stock : stocks) {
            List<Double> currentPrices = stock.getCurrentPrices();

            switch (sinistreType) {
                case CRASH:
                    double crashImpact = 1 - (random.nextDouble() * 0.4 + 0.1);
                    ajusterPrix(stock, currentPrices, crashImpact);
                    break;

                case BOOM:
                    double boomImpact = 1 + (random.nextDouble() * 0.4 + 0.1);
                    ajusterPrix(stock, currentPrices, boomImpact);
                    break;

                case STAGNATION:
                    System.out.println("Pas de changement pour l'action " + stock.getCompany());
                    break;

                case VOLATILITY_SPIKE:
                    double volatilityImpact = 1 + (random.nextDouble() - 0.5); // Hausse ou baisse aléatoire (-50% à +50%)
                    ajusterPrix(stock, currentPrices, volatilityImpact);
                    break;

                case MARKET_CORRECTION:
                    double correctionImpact = 1 - (random.nextDouble() * 0.2 + 0.05); // Réduction modérée 5%-20%
                    ajusterPrix(stock, currentPrices, correctionImpact);
                    break;

                case HYPER_GROWTH:
                    double growthImpact = 1 + (random.nextDouble() * 1.0 + 0.5); // Augmentation 50%-150%
                    ajusterPrix(stock, currentPrices, growthImpact);
                    break;

                case MARKET_PANIC:
                    double panicImpact = 1 - (random.nextDouble() * 0.7 + 0.3); // Baisse massive 30%-100%
                    ajusterPrix(stock, currentPrices, panicImpact);
                    break;

                case DIVIDEND_CUT:
                    double dividendImpact = 1 - (random.nextDouble() * 0.2); // Baisse légère 0%-20%
                    ajusterPrix(stock, currentPrices, dividendImpact);
                    break;

                case MERGER_BOOST:
                    double mergerBoostImpact = 1 + (random.nextDouble() * 0.5 + 0.2); // Augmentation 20%-70%
                    ajusterPrix(stock, currentPrices, mergerBoostImpact);
                    break;

                case SECTOR_CRISIS:
                    double sectorCrisisImpact = 1 - (random.nextDouble() * 0.5); // Réduction 0%-50%
                    ajusterPrix(stock, currentPrices, sectorCrisisImpact);
                    break;

                case INFLATION_SHOCK:
                    double inflationShockImpact = 1 - (random.nextDouble() * 0.3 + 0.1); // Réduction 10%-40%
                    ajusterPrix(stock, currentPrices, inflationShockImpact);
                    break;

                case CYBER_ATTACK:
                    double cyberAttackImpact = 1 - (random.nextDouble() * 0.6 + 0.2); // Réduction 20%-80%
                    ajusterPrix(stock, currentPrices, cyberAttackImpact);
                    break;

                case NATURAL_DISASTER:
                    double disasterImpact = 1 - (random.nextDouble() * 0.5 + 0.1); // Réduction 10%-60%
                    ajusterPrix(stock, currentPrices, disasterImpact);
                    break;

                default:
                    System.out.println("Type de sinistre non reconnu : " + sinistreType);
                    break;
            }
        }
    }


    private void ajusterPrix(Stock stock, List<Double> currentPrices, double impact) {
        if (!currentPrices.isEmpty()) {
            Double dernierPrix = currentPrices.get(currentPrices.size() - 1); // Dernier prix
            Double nouveauPrix = dernierPrix * impact; // Calculer le nouveau prix
            currentPrices.add(nouveauPrix); // Ajouter le nouveau prix à la liste

            // Sauvegarder le changement dans la base de données
            stock.setCurrentPrices(currentPrices);
            stockRepository.save(stock);

            System.out.println("Nouveau prix pour " + stock.getCompany() + ": " + nouveauPrix);
        }
    }
}

