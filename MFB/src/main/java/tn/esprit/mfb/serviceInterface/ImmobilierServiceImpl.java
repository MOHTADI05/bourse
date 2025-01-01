package tn.esprit.mfb.serviceInterface;

import tn.esprit.mfb.entity.immobilier;

import java.util.List;

public interface ImmobilierServiceImpl {


        immobilier addimmobilier(immobilier immobilier);
        List<immobilier> findAllimmobilier();
    immobilier updatefavorite(immobilier immobilier);
    immobilier findimmobilierById(Long id);
        void deletefimmobilier(Long id);
    }




