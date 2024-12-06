package tn.esprit.trading_investissement.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.trading_investissement.Entities.SaleOrder;

import java.util.List;


public interface SaleOrderRepository extends JpaRepository<SaleOrder, Long> {
    List<SaleOrder> findByCompanyName(String companyName);

    @Query("SELECT s FROM SaleOrder s WHERE s.user.id != :userId")
    List<SaleOrder> findByUserIdNot(@Param("userId") Long userId);

    @Query("SELECT s FROM SaleOrder s JOIN FETCH s.user") // Charge les informations utilisateur
    List<SaleOrder> findAllWithUsers();


    List<SaleOrder> findByUserId(Long userId);

    List<SaleOrder> findByCompanyNameAndUserId(String companyName, Long userId);

}
