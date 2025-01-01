package tn.esprit.mfb.Services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.demande_repo;
import tn.esprit.mfb.entity.Demand;

import java.util.List;

@Service

public class demande_service {
    private final demande_repo Demande_repo ;

    public demande_service(demande_repo demandeRepo) {
        Demande_repo = demandeRepo;
    }
    @Transactional
    public List<Demand> findAllimmobilier(){
        return Demande_repo.findAll();
    }
    public  Demand findimmobilierById(Long id){

        return Demande_repo.findById(id).orElse(null);
    }

    public List<Demand> getDemandsForImmobilier(Long immobilierId) {
        return Demande_repo.findByImmobilierImmobilierId(immobilierId);
    }

}

