package tn.esprit.mfb.Services;

import tn.esprit.mfb.entity.ProfilSolvabilite;
import tn.esprit.mfb.entity.TypeUser;
import tn.esprit.mfb.entity.User;

import java.util.List;

public interface ProfilSolvabiliteService {
    ProfilSolvabilite addprofil(ProfilSolvabilite p);
    public List<ProfilSolvabilite> getAllProfiles();

    void profil(ProfilSolvabilite p);

}
