package tn.esprit.mfb.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.entity.Credit;
import tn.esprit.mfb.entity.PackC;
import tn.esprit.mfb.Repository.CreditRepository;
import tn.esprit.mfb.Repository.PackRepository;
import tn.esprit.mfb.serviceInterface.ICreditService;

import java.util.List;

@Service
@AllArgsConstructor
public class CreditServiceImp implements ICreditService {
    CreditRepository creditRepository;
    PackRepository packRepository;
    @Override
    public List<Credit> AllCredit() {
        return creditRepository.findAll();
    }

    @Override
    public Credit addCredit(Credit credit) {
        return creditRepository.save(credit);
    }
    @Override
    public Credit updateCredit(Long id, Credit credit) {
        return creditRepository.findById(id)
                .map(c-> {
                    c.setMinamount(credit.getMinamount());
                    c.setMaxamount(credit.getMaxamount());
                    c.setDescription(credit.getDescription());
                    c.setName(credit.getName());



                    return creditRepository.save(c);
                }).orElseThrow(()-> new RuntimeException("Credit not found"));
    }

    @Override
    public String deleteCredit(Long id) {
        creditRepository.deleteById(id);
        return "Credit supp";
    }

    @Override
    public Credit addCreditAndAssignToPackC(Credit credit, Long idP) {
        PackC packC =packRepository.findById(idP).orElse(null);
        credit.setPackC(packC);


        return creditRepository.save(credit);
    }
    @Override
    public List<Credit> AllCreditByPack(Long i) {

        return creditRepository.findByPackCIdP(i);
    }

    @Override
    public Credit getCreditById(Long id) {
        return creditRepository.findById(id).orElse(null);

    }


}
