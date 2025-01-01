package tn.esprit.mfb.Services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tn.esprit.mfb.entity.*;
import tn.esprit.mfb.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.entity.*;
import tn.esprit.mfb.entity.Amortissement;
import tn.esprit.mfb.Repository.*;
import tn.esprit.mfb.serviceInterface.IDemandeCreditService;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@AllArgsConstructor
public class DemandeCreditService implements IDemandeCreditService {
    DemaneCreditRepository demaneCreditRepository;
    CreditRepository creditRepository;
    UserRepository userRepo;
    FundRepository fundRepository;
    HistoryCRepository historyCRepository;
    GarantorRepo garantorRepo;
    AmortisementRepo amortisementRepo;
    @Override
    public DemandeCredit add(Long existingCreditId, Long Id_fund, Long Id_client, Long Id_garantor, float year, TypeCredit typeC, float amount, LocalDate monthlyPaymentDate) {
        User client = userRepo.findById(Id_client).orElse(null);
        Fund fund=fundRepository.findById(Id_fund).orElse(null);
        Garantor Garant= garantorRepo.findById(Id_garantor).orElse(null);


        Integer Interest;
        Optional<Credit> existingCreditOptional = creditRepository.findById(existingCreditId);


        if (existingCreditOptional.isEmpty()) {
            throw new IllegalArgumentException("Le crédit choisi n'existe pas");
        }
        Credit existingCredit = existingCreditOptional.get();

        // Créer un nouveau crédit basé sur le crédit existant
        DemandeCredit newCredit = new DemandeCredit();
        newCredit.setName(existingCredit.getName());
        newCredit.setMinamount(existingCredit.getMinamount());
        newCredit.setMaxamount(existingCredit.getMaxamount());
        if (amount <= existingCredit.getMinamount() || amount >= existingCredit.getMaxamount()) {
            throw new IllegalArgumentException("Le montant doit être compris entre " + existingCredit.getMinamount() + " et " + existingCredit.getMaxamount());
        }
        newCredit.setTypeCredit(typeC);
        newCredit.setYear(year);

        switch (typeC) {
            case EMPRUNT_ANFINE:
                if (year > 5) {
                    Interest = 14;
                } else {
                    Interest = 12;
                }
                newCredit.setInterest(Interest);
                newCredit.setDiffere(true);
                newCredit.setDIFF_period(year-1);
            float yearlyInterest = amount * (float) Interest / 100;
                float monthlyPaymentI = yearlyInterest / 12; // Montant mensuel à payer pour les intérêts
                float totalInterestPaid = yearlyInterest * year; // Total des intérêts payés sur toute la durée du crédit
                float totalAmountPaid = amount + totalInterestPaid; // Montant total payé
                float monthlyPaymentLastYear = totalAmountPaid / (year - 1) / 12; // Montant mensuel à payer la dernière année avec le capital
            newCredit.setMounthlypayment(monthlyPaymentI);
            List<Amortissement> amortissements = new ArrayList<>();
            // Logique de paiement des intérêts pendant toute la durée du crédit
            float remainingAmountAffine = (amount+ monthlyPaymentI)/12;
            for (int i = 0; i < year - 1; i++) {
                // Ajouter les détails du paiement à la liste des paiements mensuels
                amortissements.add(new Amortissement(remainingAmountAffine, monthlyPaymentI, 0, monthlyPaymentI, newCredit));
            }
            // Dernière année, payer le montant total avec les intérêts
            amortissements.add(new Amortissement(amount, monthlyPaymentI,amount , remainingAmountAffine, newCredit));
            newCredit.setAmortissementList(amortissements);
            break;

            case AMORTISSEMENT_CONSTANT:
                float amortissement = amount / year;
                if (year > 5) {
                    Interest = 14;
                } else {
                    Interest = 12;
                }
                newCredit.setDiffere(false);
                newCredit.setInterest(Interest);
                float monthlyInterestRateA = (float) (Interest / 100.0 / 12.0);

                float remainingAmount = amount;
                List<Amortissement> amortissementss = new ArrayList<>();
                for (int i = 0; i < year * 12; i++) {
                    float interest = remainingAmount * monthlyInterestRateA;
                    float monthlyPaymentA = amortissement + interest;
                    amortissementss.add(new Amortissement(remainingAmount, interest, amortissement, monthlyPaymentA, newCredit));
                    newCredit.setMounthlypayment(monthlyPaymentA);
                    remainingAmount -= amortissement;
                    if (remainingAmount <= 0) {
                        break;
                    }
                }
                newCredit.setAmortissementList(amortissementss);
                break;

            case ANNUITE_CONSTANTE:
                if (year > 5) {
                    Interest = 14; // Si la durée est supérieure à 5 ans, définissez le taux d'intérêt à 4%
                } else {
                    Interest = 12; // Sinon, définissez le taux d'intérêt à 12%
                }

                // Définir le taux d'intérêt
                newCredit.setInterest(Interest);
                newCredit.setDiffere(false);
                // Calculer le montant mensuel à payer
                float monthlyInterestRate = (float) (Interest / 100.0 / 12.0); // Convertir le taux d'intérêt en taux mensuel
                float denominator = (float) Math.pow(1 + monthlyInterestRate, -year * 12);
                float monthlyPaymentE = ((amount * monthlyInterestRate) / (1 - denominator));

                // Définir le montant mensuel à payer
                newCredit.setMounthlypayment((float) Math.round(monthlyPaymentE));
                break;

            default:
                throw new IllegalArgumentException("Type de crédit non pris en charge: " + typeC);
        }

        // Définir les autres attributs spécifiques au nouveau crédit
        newCredit.setGarantor(Garant);
        newCredit.setClient(client);
        newCredit.setFunds(fund);
        LocalDate currentDate = LocalDate.now();
        newCredit.setDemandedate(currentDate);
        newCredit.setAmount(amount);
        newCredit.setMonthlyPaymentDate(monthlyPaymentDate);
            //NEW CLIENT
        if(client.getRole().equals(TypeUser.CLIENT)) {


            if (client.getCredit_authorization() == null) {   //tester sur le risk(client nouveau)
                //NB LE TAUX DE RISQUE 1%<R<2.5%
                if (1.5 * Garant.getSalaryGarantor() >= newCredit.getAmount()) {
                    //CALCUL RISK
                    newCredit.setRisk((float) (0.01 + newCredit.getAmount() / (Garant.getSalaryGarantor() * 100)));
                    Acceptation(newCredit, fund, "NouveauClient avec garant certifié");

                } else {
                    newCredit.setState(false);
                    newCredit.setReason("Salaire garant insuffisant il doit etre egale à 0.33*montant du crédit");
                }

            }
            //EXISTING CLIENT
            else if (client.getCredit_authorization() == true) {      //Ratio retard=late_days/period_of credit
                float Ratio_retard = (CaculateLateDays(demaneCreditRepository.findTopByClientCinAndCompletedIsTrueAndStateIsTrueOrderByObtainingdateDesc(Id_client))) / (demaneCreditRepository.findTopByClientCinAndCompletedIsTrueAndStateIsTrueOrderByObtainingdateDesc(Id_client).getYear() * 12 * 30);
                //3 CAS
                if (Ratio_retard < 0.1) {
                    newCredit.setRisk((float) 0.1);
                    Acceptation(newCredit, fund, "Ancien client avec un BON risque ");
                } else if (Ratio_retard >= 0.1 && Ratio_retard <= 0.25) {
                    newCredit.setRisk(Ratio_retard);
                    Acceptation(newCredit, fund, "Ancien client avec un risque modérable ");
                } else {
                    newCredit.setState(false);
                    newCredit.setReason("Client trop Risqué Mauvais Historique");
                    client.setCredit_authorization(false);    //blackLIster le client
                    userRepo.save(client);
                }

            }
            //BLACK LISTED CLIENT
            else {
                newCredit.setState(false);
                newCredit.setReason("Interdiction de Crédit");
            }
            // Enregistrer le nouveau crédit dans la base de données
            return demaneCreditRepository.save(newCredit);
        }else{
            newCredit.setReason("Interdiction de Crédit");
            return null;

        }


    }

    public void Acceptation(DemandeCredit credit,Fund fund, String msg) {

        //CALCUL DU TAUX D'INTERET
        credit.setInterest(credit.getRisk() );
        //changement du fond de la banque
        if (fund.getAmountFund() - credit.getAmount() > 0) {
            fund.setAmountFund(fund.getAmountFund() - credit.getAmount());
            credit.setState(true);
            LocalDate date = LocalDate.now();
            credit.setObtainingdate(date);

            if (credit.getDiffere() == false) { //System.out.println(Calcul_mensualite(credit));
                //credit.setMounthlypayment(Calcul_mensualite(credit));
                credit.setReason(msg);
                credit.getClient().setCredit_authorization(false);
                credit.setCompleted(false);
                fundRepository.save(fund);

            }
            //si credit avec différé
            else if (credit.getDiffere() == true) {
                credit.setAmount(credit.getAmount() + (1 + (credit.getYear() * credit.getInterest())));
                //credit.setMounthlypayment(Calcul_mensualite(credit));
                //changement de la date de paiement prevue selon la periode du differe

                credit.setMonthlyPaymentDate(
                        Date.valueOf(
                                credit.getMonthlyPaymentDate().plusMonths((long) (credit.getDIFF_period() * 12))
                        ).toLocalDate()
                );

                credit.setReason(msg);
                credit.getClient().setCredit_authorization(false);
                credit.setCompleted(false);
                fundRepository.save(fund);
            }


        } else {
            credit.setState(false);
            credit.setCompleted(false);
            credit.setReason("fond insuffisant");
        }

    }

    public int CaculateLateDays(DemandeCredit cr) {

        int S = 0;

        List<HistoryC> ListDH = historyCRepository.findByDemandecreditsIdDemandecredit(cr.getIdDemandecredit());
        for (HistoryC DH : ListDH) {
            LocalDate end = DH.getDateHistory();
            LocalDate begin = DH.getSupposedDate();
            //difference entre deux dates en jours
            long diffInDays = ChronoUnit.DAYS.between(begin, end);

            S = (int) (S + (diffInDays));


        }
        return S;
    }

    @Override
    public DemandeCredit retrieveCredit(Long idCredit) {
        DemandeCredit credit= demaneCreditRepository.findById(idCredit).orElse(null);
        return credit;
    }

    @Override
    public DemandeCredit updateCredit(Long existingCreditId, Long id_fund, Long Id_client, Long Id_garantor, float year, TypeCredit typeC, float amount, LocalDate monthlyPaymentDate) {
        User client = userRepo.findById(Id_client).orElse(null);
        Fund fund=fundRepository.findById(id_fund).orElse(null);
        Garantor Garant= garantorRepo.findById(Id_garantor).orElse(null);

        Integer Interest;
        Optional<Credit> existingCreditOptional = creditRepository.findById(existingCreditId);
        if (existingCreditOptional.isEmpty()) {
            throw new IllegalArgumentException("Le crédit choisi n'existe pas");
        }
        Credit existingCredit = existingCreditOptional.get();

        // Créer un nouveau crédit basé sur le crédit existant
        DemandeCredit newCredit = new DemandeCredit();
        newCredit.setName(existingCredit.getName());
        newCredit.setMinamount(existingCredit.getMinamount());
        newCredit.setMaxamount(existingCredit.getMaxamount());
        if (amount <= existingCredit.getMinamount() || amount >= existingCredit.getMaxamount()) {
            throw new IllegalArgumentException("Le montant doit être compris entre " + existingCredit.getMinamount() + " et " + existingCredit.getMaxamount());
        }
        newCredit.setTypeCredit(typeC);
        newCredit.setYear(year);

        switch (typeC) {
            case EMPRUNT_ANFINE:
                if (year > 5) {
                    Interest = 14;
                } else {
                    Interest = 12;
                }
                newCredit.setInterest(Interest);

                newCredit.setDiffere(true);
                newCredit.setDIFF_period(year-1);
                float yearlyInterest = amount * (float) Interest / 100;
                float monthlyPaymentI = yearlyInterest / 12; // Montant mensuel à payer pour les intérêts
                float totalInterestPaid = yearlyInterest * year; // Total des intérêts payés sur toute la durée du crédit
                float totalAmountPaid = amount + totalInterestPaid; // Montant total payé
                float monthlyPaymentLastYear = totalAmountPaid / (year - 1) / 12; // Montant mensuel à payer la dernière année avec le capital
                newCredit.setMounthlypayment(monthlyPaymentI);
                List<Amortissement> amortissements = new ArrayList<>();
                // Logique de paiement des intérêts pendant toute la durée du crédit
                float remainingAmountAffine = (amount+ monthlyPaymentI)/12;
                for (int i = 0; i < year - 1; i++) {
                    // Ajouter les détails du paiement à la liste des paiements mensuels
                    amortissements.add(new Amortissement(remainingAmountAffine, monthlyPaymentI, 0, monthlyPaymentI, newCredit));
                }
                // Dernière année, payer le montant total avec les intérêts
                amortissements.add(new Amortissement(amount, monthlyPaymentI,amount , remainingAmountAffine, newCredit));
                newCredit.setAmortissementList(amortissements);
                break;

            case AMORTISSEMENT_CONSTANT:
                float amortissement = amount / year;
                if (year > 5) {
                    Interest = 14;
                } else {
                    Interest = 12;
                }
                newCredit.setDiffere(false);
                newCredit.setInterest(Interest);
                float monthlyInterestRateA = (float) (Interest / 100.0 / 12.0);

                float remainingAmount = amount;
                List<Amortissement> amortissementss = new ArrayList<>();
                for (int i = 0; i < year * 12; i++) {
                    float interest = remainingAmount * monthlyInterestRateA;
                    float monthlyPaymentA = amortissement + interest;
                    amortissementss.add(new Amortissement(remainingAmount, interest, amortissement, monthlyPaymentA, newCredit));
                    newCredit.setMounthlypayment(monthlyPaymentA);
                    remainingAmount -= amortissement;
                    if (remainingAmount <= 0) {
                        break;
                    }
                }
                newCredit.setAmortissementList(amortissementss);
                break;

            case ANNUITE_CONSTANTE:
                if (year > 5) {
                    Interest = 14; // Si la durée est supérieure à 5 ans, définissez le taux d'intérêt à 4%
                } else {
                    Interest = 12; // Sinon, définissez le taux d'intérêt à 12%
                }

                // Définir le taux d'intérêt
                newCredit.setInterest(Interest);
                newCredit.setDiffere(false);
                // Calculer le montant mensuel à payer
                float monthlyInterestRate = (float) (Interest / 100.0 / 12.0); // Convertir le taux d'intérêt en taux mensuel
                float denominator = (float) Math.pow(1 + monthlyInterestRate, -year * 12);
                float monthlyPaymentE = ((amount * monthlyInterestRate) / (1 - denominator));

                // Définir le montant mensuel à payer
                newCredit.setMounthlypayment((float) Math.round(monthlyPaymentE));
                break;

            default:
                throw new IllegalArgumentException("Type de crédit non pris en charge: " + typeC);
        }

        // Définir les autres attributs spécifiques au nouveau crédit
        newCredit.setGarantor(Garant);
        newCredit.setClient(client);
        newCredit.setFunds(fund);
        LocalDate currentDate = LocalDate.now();
        newCredit.setDemandedate(currentDate);
        newCredit.setAmount(amount);
        newCredit.setMonthlyPaymentDate(monthlyPaymentDate);
        return demaneCreditRepository.save(newCredit);
    }

    @Override
    public void DeleteCredit(Long id) {
        demaneCreditRepository.deleteById(id);
    }


    @Override
    public Amortissement Simulateur(DemandeCredit credit) {
        System.out.println(credit.getAmount());
        Amortissement simulator = new Amortissement();
        //mnt total
        simulator.setMontantR(0);

        //mnt interet
        simulator.setInterest(0);
        float Interest;
        //mnt monthly
        if (credit.getYear() > 5) {
            Interest = 14; // Si la durée est supérieure à 5 ans, définissez le taux d'intérêt à 4%
        } else {
            Interest = 12; // Sinon, définissez le taux d'intérêt à 12%
        }
        credit.setInterest(Interest);
        float monthlyInterestRate = (float) (Interest/ 100.0 / 12.0); // Convertir le taux d'intérêt en taux mensuel
        float denominator = (float) Math.pow(1 + monthlyInterestRate, -credit.getYear() * 12);
        float monthlyPaymentE = ((credit.getAmount() * monthlyInterestRate) / (1 - denominator));
        simulator.setMensualite(monthlyPaymentE);

        Amortissement[] Credittab = TabAmortissement(credit);

        float s1 = 0;
        for (int i = 0; i < Credittab.length; i++) {
            s1 = (float) (s1 + Credittab[i].getInterest());
        }
        //mnt interet
        simulator.setInterest(s1);
        //mnt total
        simulator.setAmortissement(credit.getAmount() + s1);
        //mnt credit
        simulator.setMontantR(credit.getAmount());


        return simulator;
    }

    @Override
    public Amortissement[] TabAmortissement(DemandeCredit cr) {
        float interest = cr.getInterest() / 12;
        System.out.println("intereeeeeeeeeeeettt");
        System.out.println(cr.getInterest());
        int leng = (int) (cr.getYear() * 12);
        System.out.println("perioddeeeeeeee");
        System.out.println(cr.getYear() * 12);
        System.out.println(leng);

        Amortissement[] ListAmortissement = new Amortissement[leng];

        Amortissement amort = new Amortissement();
        //System.out.println(cr.getAmount());


        amort.setMontantR(cr.getAmount());
        float monthlyInterestRate = (float) (cr.getInterest() / 100.0 / 12.0); // Convertir le taux d'intérêt en taux mensuel
        float denominator = (float) Math.pow(1 + monthlyInterestRate, -cr.getYear() * 12);
        float monthlyPaymentE = ((cr.getAmount() * monthlyInterestRate) / (1 - denominator));
        amort.setMensualite(monthlyPaymentE);
        amort.setInterest(amort.getMontantR() * interest);
        amort.setAmortissement(amort.getMensualite() - amort.getInterest());
        ListAmortissement[0] = amort;

        //System.out.println(ListAmortissement[0]);
        for (int i = 1; i < cr.getYear() * 12; i++) {
            Amortissement amortPrecedant = ListAmortissement[i - 1];
            Amortissement amortNEW = new Amortissement();
            amortNEW.setMontantR(amortPrecedant.getMontantR() - amortPrecedant.getAmortissement());
            amortNEW.setInterest(amortNEW.getMontantR() * interest);
            amortNEW.setMensualite(monthlyPaymentE);
            amortNEW.setDemandeCredit(cr);
            amortNEW.setAmortissement(amortNEW.getMensualite() - amortNEW.getInterest());
            ListAmortissement[i] = amortNEW;

        }
        return ListAmortissement;
    }

    @Override
    public float Calcul_mensualite(DemandeCredit cr) {
        float montant = cr.getAmount();
        float tauxmensuel = cr.getInterest() / 12;
        float period = cr.getYear() * 12;
        float mensualite = (float) ((montant * tauxmensuel) / (1 - (Math.pow((1 + tauxmensuel), -period))));
        return mensualite;
    }

    @Override
    public List<Amortissement> getAllAmortissementsByUser(Long id_user) {

        User user= userRepo.findById(id_user).orElse(null);
        return amortisementRepo.findByDemandeCreditClient(user);
    }

    @Override
    public DemandeCredit findById(Long id) {
        DemandeCredit demandeCredit = demaneCreditRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Demande de crédit non trouvée"));

        // Charger explicitement la collection amortissementList
        demandeCredit.getAmortissementList().size(); // Ou accédez à chaque élément de la liste

        return demandeCredit;
    }

    @Override
    public List<DemandeCredit> findAllCreditByClient(Long id_client) {
        return demaneCreditRepository.findByClientCin(id_client);
    }

    @Override
    public Map<Integer, Float> getMontantRestantParAnnee(List<Amortissement> amortissementList) {

        Map<Integer, Float> montantRestantParAnnee = new HashMap<>();

        // Parcours de la liste d'amortissement
        for (int i = 0; i < amortissementList.size(); i++) {
            Amortissement amortissement = amortissementList.get(i);
            // Ajout du montant restant pour chaque année
            montantRestantParAnnee.put(i + 1, amortissement.getMontantR());
        }

        return montantRestantParAnnee;

    }

}