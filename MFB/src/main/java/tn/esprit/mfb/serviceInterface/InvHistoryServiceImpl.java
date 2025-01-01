package tn.esprit.mfb.serviceInterface;

import tn.esprit.mfb.entity.inv_history;

import java.util.List;

public interface InvHistoryServiceImpl {
        inv_history addinv_history(inv_history invHistory);
        List<inv_history> findAllinv_history();
        inv_history updateinv_history(inv_history invHistory);
        inv_history findinv_historyById(Long id);
        void deleteinv_history(Long id);
    }

