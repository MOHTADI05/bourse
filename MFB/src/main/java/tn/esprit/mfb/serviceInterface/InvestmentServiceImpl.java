package tn.esprit.mfb.serviceInterface;

import tn.esprit.mfb.entity.*;

import java.util.List;

public interface InvestmentServiceImpl {

    investisment addInvestisment(InvestismentDto investismentDto) ;
        List<investisment> findAllInvestismen();
    investisment updateInvestismen(investisment investment);
    investisment findInvestismentById(Long id);
     void deleteInvestismen(Long id) ;
     inv_history buyPercentageInApartment(Long imb, Long userId, long amount) ;
     List<inv_history> sellInvestmentByPercentage(Long imbId, Long amount) ;
     List<inv_history> sellPercentageToDemand(Long demandId, Long sellerUserId) ;

    }



