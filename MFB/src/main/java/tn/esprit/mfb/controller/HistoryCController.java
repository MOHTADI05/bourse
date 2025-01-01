package tn.esprit.mfb.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.entity.HistoryC;
import tn.esprit.mfb.serviceInterface.IHistoryCService;

import java.util.List;

@RestController
@RequestMapping("/History")
@AllArgsConstructor
@CrossOrigin("*")

public class HistoryCController {
    private final IHistoryCService historyCService;
    @GetMapping("/read")
    public List<HistoryC> getDuesHistory() {
        List<HistoryC> listDuesHistorys = historyCService.retrieveAllDuesHistorys();
        return listDuesHistorys;
    }
    @GetMapping("/read/{id}")
    public List<HistoryC> getDuesHistoryByCredit(@PathVariable("idcredit") Long idcredit) {
    List<HistoryC> listDuesHistorys = historyCService.retrieveAllDuesHistory_byCredit(idcredit);
    return listDuesHistorys;
}
    @PostMapping("/add/{id_credit}")

    public HistoryC addDuesHistory(@RequestBody HistoryC c,@PathVariable("id_credit") Long id_credit)
    {
        HistoryC DuesHistory = historyCService.addDuesHistory(c,id_credit);
        return DuesHistory;
    }
    @PutMapping("/modify/{Id_client}")
    public HistoryC modifyDuesHistory(@RequestBody HistoryC HistoryC,@PathVariable("DuesHistory-Id_client") Long Id_client) {
        return historyCService.updateDuesHistory(HistoryC,Id_client);
    }

    //http://localhost:8083/BKFIN/DuesHistory/remove-DuesHistory/{DuesHistory-id}
    @DeleteMapping("/remove/{id}")
    @ResponseBody
    public void removeDuesHistory(@PathVariable("id") Long DuesHistoryId) {
        historyCService.DeleteDuesHistory(DuesHistoryId);
    }
}
