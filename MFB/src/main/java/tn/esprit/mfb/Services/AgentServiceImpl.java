package tn.esprit.mfb.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.AgentRepo;
import tn.esprit.mfb.Repository.UserRepository;
import tn.esprit.mfb.config.JwtBlacklistService;
import tn.esprit.mfb.config.JwtService;
import tn.esprit.mfb.entity.TalentReview;
import tn.esprit.mfb.entity.TypeUser;
import tn.esprit.mfb.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final UserRepository Repuser;
    private final AgentRepo agentRepo;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtBlacklistService jwtBlacklistService;
    private final EmailSenderService emailSenderService;

    @Override
    public User addAgent(User a) {
        String encodedPassword = passwordEncoder.encode(a.getPassword());
        a.setPassword(encodedPassword);
        a.setRole(TypeUser.AGENT);
        a.setClassification(TalentReview.notbeenTested);
        Repuser.save(a);
        String resetUrl = "http://localhost:8084/api/admin/unblock/"+a.getCin();
        emailSenderService.sendSimpleEmail("aissa.swiden@esprit.tn","new agent","to unlock his account enter this url: " + resetUrl );

        return a;
    }

    @Override
    public List<User> retrieveAllAgent(TypeUser role) {
        return agentRepo.findByRole(role);    }

    @Override
    public List<User> retrieveAgentByClass(TalentReview c) {
        return null;
    }

    @Override
    public User SaveClassification9box(String per, String pot, Long id) {
        User user = Repuser.findByCin(id);
        user.setPerformance(per);
        user.setPotentiel(pot);
        Repuser.save(user);

        return user;    }

    @Override
    public TalentReview GetClassification9box(Long id) {
        User user = Repuser.findByCin(id);


        if ((user.getPerformance().equals("below expected"))&&(user.getPotentiel().equals("low")))
        {
            user.setClassification(TalentReview.Underperformer);
            Repuser.save(user);
        }
        else if ((user.getPerformance().equals("below expected"))&&(user.getPotentiel().equals("moderate")))
        {


            user.setClassification(TalentReview.InconsistentPlayer);
            Repuser.save(user);
        }
        else if ((user.getPerformance().equals("below expected"))&&(user.getPotentiel().equals("high")))
        {


            user.setClassification(TalentReview.RoughDiamond);
            Repuser.save(user);
        }
        else if ((user.getPerformance().equals("moderate"))&&(user.getPotentiel().equals("low")))
        {


            user.setClassification(TalentReview.EffectiveEmployee);
            Repuser.save(user);
        }
        else if ((user.getPerformance().equals("moderate"))&&(user.getPotentiel().equals("moderate")))
        {


            user.setClassification(TalentReview.CoreEmployee);
            Repuser.save(user);
        }
        else if ((user.getPerformance().equals("moderate"))&&(user.getPotentiel().equals("high")))
        {



            user.setClassification(TalentReview.FutureStar);
            Repuser.save(user);
        }
        else if ((user.getPerformance().equals("above expected"))&&(user.getPotentiel().equals("low")))
        {


            user.setClassification(TalentReview.TrustedProfessionel);
            Repuser.save(user);
        }
        else if ((user.getPerformance().equals("above expected"))&&(user.getPotentiel().equals("moderate")))
        {


            user.setClassification(TalentReview.HighImpactStar);
            Repuser.save(user);		}
        else if ((user.getPerformance().equals("above expected"))&&(user.getPotentiel().equals("high")))
        {


            user.setClassification(TalentReview.ConisitentStar);
            Repuser.save(user);
        }
        else

        {
            user.setClassification(TalentReview.notbeenTested);
            Repuser.save(user);

        }
        return user.getClassification();
    }


}
