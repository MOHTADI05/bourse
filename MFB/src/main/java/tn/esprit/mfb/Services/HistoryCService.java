package tn.esprit.mfb.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.UserRepository;
import tn.esprit.mfb.entity.DemandeCredit;
import tn.esprit.mfb.entity.HistoryC;
import tn.esprit.mfb.Repository.DemaneCreditRepository;
import tn.esprit.mfb.Repository.HistoryCRepository;
import tn.esprit.mfb.serviceInterface.IHistoryCService;

import java.util.List;
@Service
@AllArgsConstructor

public class HistoryCService implements IHistoryCService {
    HistoryCRepository historyCRepository;
    UserRepository userRepo;
    DemaneCreditRepository demaneCreditRepository;
    DemandeCreditService demandeCreditService;
    @Override
    public List<HistoryC> retrieveAllDuesHistorys() {
        return (List<HistoryC>) historyCRepository.findAll();
    }

    @Override
    public List<HistoryC> retrieveAllDuesHistory_byCredit(Long idCredit) {
        return (List<HistoryC>) historyCRepository.findByDemandecreditsIdDemandecredit(idCredit);

    }

    @Override
    public HistoryC addDuesHistory(HistoryC DH, Long idcredit) {

        DemandeCredit credit= demaneCreditRepository.findById(idcredit).orElse(null);

        DH.setDemandecredits(credit);
        //credit incomplete save the dues history
        if(!DH.getDemandecredits().getCompleted())
        {
            //add supposed date ++
            if (historyCRepository.findMaxSupposedDateByDemandecreditsIdDemandecredit(idcredit)==null)
            { DH.setSupposedDate(credit.getMonthlyPaymentDate());

            }
            else {
                DH.setSupposedDate(historyCRepository.findMaxSupposedDateByDemandecreditsIdDemandecredit(idcredit).plusMonths(1));
                }



            //calcul du montant total du credit
            float amount_topay=(demandeCreditService.Calcul_mensualite(credit)*(int) (credit.getYear()*12));

            //compare payed amount with creditamount to pay
            if(amount_topay<=(PayedAmount(idcredit)+DH.getDemandecredits().getMounthlypayment()))
            {//add supposed date ++

                DH.getDemandecredits().setCompleted(true);
                demaneCreditRepository.save(credit);
                DH.getDemandecredits().getClient().setCredit_authorization(true);
                userRepo.save(DH.getDemandecredits().getClient());
                historyCRepository.save(DH);
            }
            else
            {
                historyCRepository.save(DH);
            }

        }
        else
        {System.out.println("credit payé deja");}

        return DH;
    }

    @Override
    public HistoryC updateDuesHistory(HistoryC DH, Long idcredit) {
        DemandeCredit credit= demaneCreditRepository.findById(idcredit).orElse(null);
        DH.setDemandecredits(credit);
        historyCRepository.save(DH);
        return DH;
    }

    @Override
    public HistoryC retrieveDuesHistory(Long idDuesHistory) {
        HistoryC DH= historyCRepository.findById(idDuesHistory) .orElse(null) ;
        return DH ;
    }

    @Override
    public void DeleteDuesHistory(Long idDuesHistory) {
            historyCRepository.deleteById(idDuesHistory);

    }

    @Override
    public float PayedAmount(Long idcredit) {
        DemandeCredit credit= demaneCreditRepository.findById(idcredit).orElse(null);
        List<HistoryC> ListDH =historyCRepository.findByDemandecreditsIdDemandecredit(idcredit);
        float payed_amount=ListDH.size()*demandeCreditService.Calcul_mensualite(credit);
        return payed_amount;
    }
}
