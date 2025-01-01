package tn.esprit.mfb.Services;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.entity.ProfilSolvabilite;
import tn.esprit.mfb.entity.clusterenum.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
@Service
public class CsvExporter {

    public static void exportDataToCSV(List<ProfilSolvabilite> data, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Écrire l'en-tête du fichier CSV
            writer.append("RevenuMensuel,ChargeMensuelle,Ratiodetterevenu,Anciennete ,Epargne,Statutemploi,Chargefamille,MontantDette,age,education,logement,matrimone,Region\n");

            // Écrire les données dans le fichier CSV

            for (ProfilSolvabilite profil : data) {

                writer.append(String.valueOf(profil.getRevenuMensuelNet())).append(",");
                writer.append(String.valueOf(profil.getChargeMensuel())).append(",");
                writer.append(String.valueOf(profil.getRatioDetteRevenu())).append(",");
                writer.append(String.valueOf(profil.getAncienneteEmploiActuel())).append(",");
                writer.append(String.valueOf(profil.getEpargneActifsLiquides())).append(",");
                if(profil.getStatutEmploi().equals(StatutEmploi.CDD))
                {
                     writer.append(String.valueOf(1)).append(",");
                }//enum
                if(profil.getStatutEmploi().equals(StatutEmploi.CDI))
                {
                    writer.append(String.valueOf(2)).append(",");
                }
                if(profil.getStatutEmploi().equals(StatutEmploi.ENTREPRENEUR))
                {
                    writer.append(String.valueOf(3)).append(",");
                }
                writer.append(String.valueOf(profil.getChargeFamille())).append(",");
                writer.append(String.valueOf(profil.getMontantDetteTotale())).append(",");
                writer.append(String.valueOf(profil.getAge())).append(",");
                if(profil.getNiveauEducation().equals(NiveauEdu.BAC)){
                    writer.append(String.valueOf(1)).append(",");  //enum

                }
                if(profil.getNiveauEducation().equals(NiveauEdu.LICENCE)){
                    writer.append(String.valueOf(2)).append(",");  //enum

                }
                if(profil.getNiveauEducation().equals(NiveauEdu.MASTER)){
                    writer.append(String.valueOf(3)).append(",");  //enum

                }
                if(profil.getNiveauEducation().equals(NiveauEdu.AUTRE)){
                    writer.append(String.valueOf(4)).append(",");  //enum

                }
                if(profil.getNiveauEducation().equals(NiveauEdu.SECONDAIRE)){
                    writer.append(String.valueOf(0)).append(",");  //enum

                }
                if(profil.getTypeLogement().equals(Logement.LOCATAIRE)){
                    writer.append(String.valueOf(1)).append(",");//enum

                }
                if(profil.getTypeLogement().equals(Logement.PROPRIOT)){
                    writer.append(String.valueOf(2)).append(",");//enum

                }
                if(profil.getSituationMatrimoniale().equals(Matrimoine.CELIBATAIRE)){
                    writer.append(String.valueOf(0)).append(",");    //enum

                }
                if(profil.getSituationMatrimoniale().equals(Matrimoine.DIVORCE)){
                    writer.append(String.valueOf(1)).append(",");    //enum

                }
                if(profil.getSituationMatrimoniale().equals(Matrimoine.MARIER)){
                    writer.append(String.valueOf(2)).append(",");    //enum

                }
                if(profil.getRegionResidence().equals(Region.CENTRE_EST)){
                    writer.append(String.valueOf(0));  //enum

                }
                if(profil.getRegionResidence().equals(Region.CENTRE_OUEST)){
                    writer.append(String.valueOf(1));  //enum

                }
                if(profil.getRegionResidence().equals(Region.NORD_EST)){
                    writer.append(String.valueOf(2));  //enum

                }
                if(profil.getRegionResidence().equals(Region.NORD_OUEST)){
                    writer.append(String.valueOf(2));  //enum

                }
                if(profil.getRegionResidence().equals(Region.SUD_EST)){
                    writer.append(String.valueOf(3));  //enum

                }
                if(profil.getRegionResidence().equals(Region.SUD_OUEST)){
                    writer.append(String.valueOf(3));  //enum

                }
                writer.append("\n");
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
