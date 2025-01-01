package tn.esprit.mfb.serviceInterface;

import tn.esprit.mfb.entity.Garantor;

public interface IGarantorService {

    Garantor add(Garantor garantor);
    void deleteG(Long id);
    Garantor retriveG(Long id);
}
