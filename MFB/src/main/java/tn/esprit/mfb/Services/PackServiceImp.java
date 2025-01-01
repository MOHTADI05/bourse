package tn.esprit.mfb.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import tn.esprit.mfb.entity.PackC;
import tn.esprit.mfb.Repository.PackRepository;
import tn.esprit.mfb.serviceInterface.IPackService;

import java.util.List;

@Service
@AllArgsConstructor
@CrossOrigin("*")
public class PackServiceImp implements IPackService {
    PackRepository packRepository;
    @Override
    public List<PackC> AllPack() {
        return packRepository.findAll();
    }

    @Override
    public PackC addPack(PackC packC) {
        return packRepository.save(packC);
    }

    @Override
    public PackC updatePack(Long id, PackC packC) {
        return packRepository.findById(id)
                .map(c-> {
                    c.setTypePack(packC.getTypePack());
                    c.setDescription(packC.getDescription());
                    c.setName(packC.getName());

                    return packRepository.save(c);
                }).orElseThrow(()-> new RuntimeException("Credit not found"));
    }

    @Override
    public String deletePack(Long id) {
         packRepository.deleteById(id);
        return "Pack supprimé";
    }

    @Override
    public PackC getPack(Long id) {
        return packRepository.findById(id).orElse(null);

    }
}
