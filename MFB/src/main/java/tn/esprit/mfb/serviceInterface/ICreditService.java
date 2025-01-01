package tn.esprit.mfb.serviceInterface;

import tn.esprit.mfb.entity.Credit;

import java.util.List;

public interface ICreditService {
    List<Credit> AllCredit();
    Credit addCredit(Credit credit);
    Credit updateCredit(Long id,Credit credit);
    String deleteCredit(Long id);
    Credit addCreditAndAssignToPackC(Credit credit, Long idP);
    List<Credit> AllCreditByPack(Long i);
    Credit getCreditById(Long id);

}
