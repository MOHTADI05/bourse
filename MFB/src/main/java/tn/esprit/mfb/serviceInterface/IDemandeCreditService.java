package tn.esprit.mfb.serviceInterface;

import tn.esprit.mfb.entity.Amortissement;
import tn.esprit.mfb.entity.DemandeCredit;
import tn.esprit.mfb.entity.TypeCredit;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IDemandeCreditService {
    DemandeCredit add(Long existingCreditId, Long id_fund, Long Id_client, Long Id_garantor, float year, TypeCredit typeC, float amount, LocalDate demandDate);
    Amortissement Simulateur(DemandeCredit credit);
    Amortissement[] TabAmortissement(DemandeCredit cr);
    float Calcul_mensualite(DemandeCredit cr);
    int CaculateLateDays(DemandeCredit  cr);
    DemandeCredit retrieveCredit(Long idCredit);

    DemandeCredit updateCredit(Long existingCreditId, Long id_fund, Long Id_client, Long Id_garantor, float year, TypeCredit typeC, float amount, LocalDate demandDate);

    DemandeCredit findById(Long id);
    List<DemandeCredit> findAllCreditByClient(Long id_client);
    Map<Integer, Float> getMontantRestantParAnnee(List<Amortissement> amortissementList);
    List<Amortissement> getAllAmortissementsByUser(Long id_user);
     void DeleteCredit(Long id);
}
