package tn.esprit.mfb.Services;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.PaymentRepository;
import tn.esprit.mfb.entity.Payment;
import tn.esprit.mfb.serviceInterface.PaymentService;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private  PaymentRepository paymentRepository;
    private final NumberFormat nfPercent;
    private final NumberFormat nfCurrency;


    public Payment getPaymentByNumber(int paymentNumber) {
        return paymentRepository.findByPaymentNumber(paymentNumber);
    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);

    }
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment confirmPayment(int paymentNumber) {
        Payment paymentToConfirm = getPaymentByNumber(paymentNumber);

        paymentToConfirm.setConfirmed(true);
        return paymentRepository.save(paymentToConfirm);
    }

    public Payment updatePayment(int paymentNumber, Payment updatedPayment) {
        // Récupérer le paiement existant en utilisant le numéro de paiement passé en paramètre

        Payment existingPayment = getPaymentByNumber(paymentNumber);

     // Ajoutez ici la logique de validation et de traitement avant de mettre à jour le paiement

        // Mettre à jour la date de paiement du paiement existant avec la date de paiement de l'objet mis à jour
        existingPayment.setPaymentDate(updatedPayment.getPaymentDate());
        // Mettre à jour le solde du paiement existant avec le solde de l'objet mis à jour

        existingPayment.setBalance(updatedPayment.getBalance());
        // Mettre à jour le montant principal payé du paiement existant avec le montant principal payé de l'objet mis à jour

        existingPayment.setPrincipalPaid(updatedPayment.getPrincipalPaid());
        // Mettre à jour le montant des intérêts payés du paiement existant avec le montant des intérêts payés de l'objet mis à jour

        existingPayment.setInterestPaid(updatedPayment.getInterestPaid());
        // Mettre à jour l'intérêt accumulé du paiement existant avec l'intérêt accumulé de l'objet mis à jour

        existingPayment.setAccumulatedInterest(updatedPayment.getAccumulatedInterest());
        // Enregistrer les modifications apportées au paiement existant dans le référentiel de paiement

        return paymentRepository.save(existingPayment);
    }

    public void deletePayment(int paymentNumber) {
        Payment payment = getPaymentByNumber(paymentNumber);
        paymentRepository.delete(payment);
    }
    @Autowired
  PaymentServiceImpl(PaymentRepository paymentRepository)
    {
    this.paymentRepository=paymentRepository ;
        //percentage formatter
        // Création d'une instance du formateur de pourcentage

        nfPercent = NumberFormat.getPercentInstance();
        // Définition du nombre minimum de chiffres fractionnaires à afficher pour le pourcentage (2 chiffres après la virgule)

        nfPercent.setMinimumFractionDigits(2);
        // Définition du nombre maximum de chiffres fractionnaires à afficher pour le pourcentage (4 chiffres après la virgule)

        nfPercent.setMaximumFractionDigits(4);

        //currency formatter
        // Création d'une instance du formateur de devise

        nfCurrency = NumberFormat.getCurrencyInstance();
        // Définition du nombre minimum de chiffres fractionnaires à afficher pour la devise (2 chiffres après la virgule)

        nfCurrency.setMinimumFractionDigits(2);
        // Définition du nombre maximum de chiffres fractionnaires à afficher pour la devise (2 chiffres après la virgule)

        nfCurrency.setMaximumFractionDigits(2);
    }


    @Override
    public String formatCurrency(double number) {
        return nfCurrency.format(number);
    }

    @Override
    public String formatPercent(double number) {
        return nfPercent.format(number);
    }

    @Override
    public double stringToPercent(String s) throws ParseException {
        return nfPercent.parse(s).doubleValue();
    }

    @Override
    public double getMonthlyInterestRate(double interestRate) {
        return interestRate / 100 / 12;
    }
//calcule le paiement périodique (PMT)
    @Override
    public double pmt(double r, int nper, double pv, double fv, int type) {
        if (r == 0) {
            // Si le taux d'intérêt est nul, calculer le PMT directement sans considérer le taux d'intérêt

            return -(pv + fv) / nper;
        }
        // Calculer le PMT en utilisant la formule de PMT pour un prêt ou une annuité

        double pmt = r / (Math.pow(1 + r, nper) - 1) * -(pv * Math.pow(1 + r, nper) + fv);

        if (type == 1) {
            // Si le type de paiement est différé, ajuster le PMT en divisant par (1 + r)

            pmt /= (1 + r);
        }

        return pmt;
    }

    @Override
    public double pmt(double r, int nper, double pv, double fv) {
        return pmt(r, nper, pv, fv, 0);
    }

    @Override
    public double pmt(double r, int nper, double pv) {
        return pmt(r, nper, pv, 0, 0);
    }
// Méthode pour calculer la valeur future (FV) d'un investissement ou d'une dette

    @Override
    public double fv(double r, int nper, double c, double pv, int type) {
        if (r == 0) return pv;// Si le taux d'intérêt est nul, la valeur future est égale à la valeur présente


        // Si le type de paiement est différé, ajuster le montant périodique en multipliant par (1 + r)

        //we are going in reverse, we multiply by 1 plus interest rate.
        if (type == 1) {
            c *= (1 + r);
        }
        // Calculer la valeur future en utilisant la formule de la valeur future pour un investissement ou une dette

        double fv = -((Math.pow(1 + r, nper) - 1) / r * c + pv * Math.pow(1 + r, nper));


        return fv;
    }

    @Override
    public double fv(double r, int nper, double c, double pv) {
        return fv(r, nper, c, pv, 0);
    }
// Méthode pour calculer le montant des intérêts pour une période donnée d'un prêt ou d'une annuité

    @Override
    public double ipmt(double r, int per, int nper, double pv, double fv, int type) {
        // Calculer la valeur future au début de la période donnée en utilisant la méthode fv

        double ipmt = fv(r, per - 1, pmt(r, nper, pv, fv, type), pv, type) * r;
        // Si le type de paiement est différé (type = 1), ajuster les intérêts en les divisant par (1 + r)

        if (type == 1) {
            ipmt /= (1 + r);
        }

        return ipmt;
    }

    @Override
    public double ppmt(double r, int per, int nper, double pv, double fv, int type) {

        // Calculated payment per period - interest portion of that period.
        return pmt(r, nper, pv, fv, type) - ipmt(r, per, nper, pv, fv, type);
    }

}