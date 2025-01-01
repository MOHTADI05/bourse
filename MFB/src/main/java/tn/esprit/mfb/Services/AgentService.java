package tn.esprit.mfb.Services;

import tn.esprit.mfb.entity.TalentReview;
import tn.esprit.mfb.entity.TypeUser;
import tn.esprit.mfb.entity.User;

import java.util.List;

public interface AgentService {

    User addAgent(User a);

    List<User> retrieveAllAgent(TypeUser role);
    List<User> retrieveAgentByClass(TalentReview c);
    User SaveClassification9box( String per,String pot, Long id);
    TalentReview GetClassification9box(  Long id);

}
