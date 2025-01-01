package tn.esprit.mfb.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Services.ClusteringService;
import tn.esprit.mfb.Services.CsvExporter;
import tn.esprit.mfb.Services.ProfilSolvabiliteService;
import tn.esprit.mfb.entity.ProfilSolvabilite;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/solva")
@CrossOrigin("*")
public class ProfilSolvaController {

    final private ProfilSolvabiliteService service;
    private final CsvExporter CsvService;
    private final ClusteringService serviceCluster;
    private final ProfilSolvabiliteService serviceP;

    @PostMapping("/AddProfil")
    @ResponseBody
    public ProfilSolvabilite AjoutAgent(@RequestBody ProfilSolvabilite p) {
        return service.addprofil(p);
    }

    @GetMapping("/profilsolvabilite/export")
    public ResponseEntity<String> exportDataToCSV() {
        List<ProfilSolvabilite> profilSolvabilites = service.getAllProfiles();
        String filePath = "C:\\Users\\Asus VivoBook\\Desktop\\DATAclustert\\exportt.csv";

        try {
            CsvService.exportDataToCSV(profilSolvabilites, filePath);
            return new ResponseEntity<>("Export des données vers le fichier CSV réussi.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Une erreur s'est produite lors de l'exportation des données.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/Profil")
    @ResponseBody
    public  ResponseEntity<String > Profil(@RequestBody ProfilSolvabilite p) throws Exception {

        service.profil(p);
        int cluster;
        List<ProfilSolvabilite> profilSolvabilites = serviceP.getAllProfiles();
        String filePath = "C:\\Users\\Asus VivoBook\\Desktop\\DATAclustert\\exportt.csv";
        try {
            CsvService.exportDataToCSV(profilSolvabilites, filePath);
            System.out.println("succes export");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("echec export");
        }

        try {
            cluster  = serviceCluster.segmentation1(p);
            if(cluster==1){
                return new ResponseEntity<>("VOUS ETES SOLVABLE .", HttpStatus.OK);

            }else {
                return new ResponseEntity<>("VOUS ETES NON SOLVABLE .", HttpStatus.OK);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Une erreur s'est produite.", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
