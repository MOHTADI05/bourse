package tn.esprit.trading_investissement.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.trading_investissement.Entities.PurchasedStock;
import tn.esprit.trading_investissement.Entities.Stock;

import java.util.List;
import java.util.Optional;


public interface PurchasedStockRepository extends JpaRepository<PurchasedStock, Long> {
    List<PurchasedStock> findByCompany(String company);
    List<PurchasedStock> findByUserId(Long userId);

}

