package tn.esprit.mfb.serviceInterface;

import tn.esprit.mfb.entity.Fund;

import java.util.List;

public interface IfundService {

    List<Fund> retrieveAllFunds();

    Fund addFund(Fund f);

    void deleteFund(Long idFund);

    Fund updateFund(Fund fun);

    Fund retrieveFund(Long idFund);
}
