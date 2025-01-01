package tn.esprit.mfb.serviceInterface;

import tn.esprit.mfb.entity.PackC;

import java.util.List;

public interface IPackService {
    List<PackC> AllPack();
    PackC addPack(PackC packC);
    PackC updatePack(Long id,PackC packC);
    String deletePack(Long id);
    PackC getPack(Long id);

}
