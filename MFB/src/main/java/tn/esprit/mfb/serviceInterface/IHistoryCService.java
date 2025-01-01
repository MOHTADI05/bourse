package tn.esprit.mfb.serviceInterface;

import tn.esprit.mfb.entity.HistoryC;

import java.util.List;

public interface IHistoryCService {
    List<HistoryC> retrieveAllDuesHistorys();

    List<HistoryC> retrieveAllDuesHistory_byCredit(Long idCredit);

    HistoryC addDuesHistory (HistoryC p,Long idcredit);

    HistoryC updateDuesHistory (HistoryC p,Long idcredit);

    HistoryC retrieveDuesHistory(Long idDuesHistory);

    public void DeleteDuesHistory(Long idDuesHistory);

    float PayedAmount(Long idcredit);
}
