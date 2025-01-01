package tn.esprit.mfb.serviceInterface;


import tn.esprit.mfb.entity.Payment;

import java.text.ParseException;
import java.util.List;

public interface PaymentService {
    List<Payment> getAllPayments();

    Payment getPaymentByNumber(int paymentNumber);

    Payment createPayment(Payment payment);

    Payment updatePayment(int paymentNumber, Payment updatedPayment);

    void deletePayment(int paymentNumber);

    public String formatCurrency(double number);
    public String formatPercent(double number);
    public double stringToPercent(String s) throws ParseException;
    public double getMonthlyInterestRate(double interestRate);
    public double pmt(double r, int nper, double pv, double fv, int type);
    public double pmt(double r, int nper, double pv, double fv);
    public double pmt(double r, int nper, double pv);
    public double fv(double r, int nper, double c, double pv, int type);
    public double fv(double r, int nper, double c, double pv);
    public double ipmt(double r, int per, int nper, double pv, double fv, int type);
    public double ppmt(double r, int per, int nper, double pv, double fv, int type);

}