package tn.esprit.trading_investissement.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.trading_investissement.Entities.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByCompany(String company);

    Stock findById(long id);

}