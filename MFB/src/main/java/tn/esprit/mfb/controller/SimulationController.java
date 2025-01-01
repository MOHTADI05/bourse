package tn.esprit.mfb.controller;


import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tn.esprit.mfb.entity.Amortization;
import tn.esprit.mfb.entity.Payment;
import tn.esprit.mfb.serviceInterface.AmortizationService;

import javax.faces.context.FacesContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
@Scope(value = "session")
@Controller(value = "SimulationController")
@RequestMapping("/simu")
public class SimulationController {

    @Getter
    private static final Logger L=LogManager.getLogger(SimulationController.class);

    @Getter
    private String startDate;
    @Getter
    private double initialBalance;
    @Getter
    private double interestRate;
    @Getter
    private int durationInMonths;
    @Getter
    private double futureValue;
    @Getter
    private int paymentType;
    @Getter
    private double monthlyPayment;
    @Getter
    private List<Payment> paymentList = new ArrayList<Payment>();
    @Getter
    private Amortization amortization = new Amortization();

    @Autowired
    private AmortizationService amortizationService;


    public String doLogout() {

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";

    }


    public String doSimulate() throws ParseException{
        amortization.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(startDate));
        amortization.setInitialBalance(initialBalance);
        amortization.setDurationInMonths(durationInMonths);
        amortization.setFutureValue(futureValue);
        amortization.setInterestRate(interestRate);
        amortization.setPaymentType(paymentType);

        amortizationService.initializeUnknownFields(amortization);
        return "/simulation/schedule.xhtml?faces-redirect=true";
    }


    public void setAmortization(Amortization amortization) {
        this.amortization = amortization;
    }


    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void setDurationInMonths(int durationInMonths) {
        this.durationInMonths = durationInMonths;
    }

    public void setFutureValue(double futureValue) {
        this.futureValue = futureValue;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }


}
