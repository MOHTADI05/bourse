package tn.esprit.mfb.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.entity.ProfilSolvabilite;
import tn.esprit.mfb.Repository.ProfilSolvabiteRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfilSolvabiliteImpl implements ProfilSolvabiliteService {

    private final ProfilSolvabiteRepo Repuser;
    @Override
    public ProfilSolvabilite addprofil(ProfilSolvabilite p) {
p.setRatioDetteRevenu(p.getMontantDetteTotale()/( p.getRevenuMensuelNet() - p.getChargeMensuel() )*12);
        Repuser.save(p);
        return p;

    }

    @Override
    public List<ProfilSolvabilite> getAllProfiles() {
        return Repuser.findAll();
    }



    @Override
    public void profil(ProfilSolvabilite p) {

        p.setRatioDetteRevenu(p.getMontantDetteTotale()/( p.getRevenuMensuelNet())*12);
        Repuser.save(p);
//        List<ProfilSolvabilite> profilSolvabilites = serviceP.getAllProfiles();
//        String filePath = "C:\\Users\\USER\\Desktop\\dataset\\exportt.csv";
//        try {
//            CsvService.exportDataToCSV(profilSolvabilites, filePath);
//            System.out.println("succes export");
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("echec export");
//        }
//
//        try {
//            service.segmentation1(p);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }



    }

}

