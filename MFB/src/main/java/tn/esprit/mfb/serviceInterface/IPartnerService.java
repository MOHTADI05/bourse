package tn.esprit.mfb.serviceInterface;

import tn.esprit.mfb.entity.Partner;

import java.util.List;
import java.util.Optional;

public interface IPartnerService {
    Partner saveOrUpdatePartner(Partner partner);
    List<Partner> getAllPartners();
    Optional<Partner> getPartnerById(Long partnerId);
    void deletePartnerById(Long partnerId);
}
