package tn.esprit.mfb.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.entity.Garantor;
import tn.esprit.mfb.Repository.DemaneCreditRepository;
import tn.esprit.mfb.Repository.GarantorRepo;
import tn.esprit.mfb.serviceInterface.IGarantorService;

@Service
@AllArgsConstructor
public class GarantorService implements IGarantorService {
     GarantorRepo garantorRepo;
     DemaneCreditRepository demaneCreditRepository;
    @Override
    public Garantor add(Garantor garantor) {

        garantorRepo.save(garantor);
        return garantor;
    }

    @Override
    public void deleteG(Long id) {
          garantorRepo.deleteById(id);
    }

    @Override
    public Garantor retriveG(Long id) {
        return null;
    }
}
