package tn.esprit.mfb.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Repository.UserRepository;
import tn.esprit.mfb.Services.AgentService;
import tn.esprit.mfb.Services.UserService;
import tn.esprit.mfb.entity.TalentReview;
import tn.esprit.mfb.entity.TypeUser;
import tn.esprit.mfb.entity.User;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    AgentService agentService;
    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> GetAllUsers(){
        return userService.getAllUser();
    }

    @DeleteMapping("/deleteuser/{cin}")
    @ResponseBody
    public void deleteuser(@PathVariable("cin") Long cin){
        userService.DeleteUser(cin);
    }


    @GetMapping("/getUserByEmail/{email}")
    @ResponseBody
    public User getUserByEmail(@PathVariable("email") String email) {
        return userService.GetUser(email);
    }

    @PutMapping("/block/{cin}")
    @ResponseBody
    public void blockuser(@PathVariable("cin") Long cin){
        userService.BlockUser(cin);
    }

    @PutMapping("/unblock/{cin}")
    @ResponseBody
    public void unblockuser(@PathVariable("cin") Long cin){
        System.out.println(cin);
        userService.UnblockUser(cin);
    }

//http://localhost:8084/api/admin/AddAgent

    @PostMapping("/AddAgent")
    @ResponseBody
    public User AjoutAgent (@RequestBody User a)
    {
        return agentService.addAgent(a) ;
    }

//http://localhost:8084/api/admin/GetallAgent?role=AGENT
    @GetMapping("/GetallAgent")
    @ResponseBody
    public List<User> getallAgents(@RequestParam("role") TypeUser role) {

        return agentService.retrieveAllAgent(role);
    }

    @GetMapping("/9box/{id}/{per}/{pot}")
    @ResponseBody
    public User Talent(@PathVariable("id") long IdAgent,@PathVariable("per") String Per,@PathVariable("pot") String Pot) {
        User user = userRepository.findByCin(IdAgent);

        if (user.getRole().equals(TypeUser.AGENT)){
         user= agentService.SaveClassification9box(Per,Pot, IdAgent);
        TalentReview classi = agentService.GetClassification9box( IdAgent);
        return user;
        }
        else{
            // return ResponseEntity.badRequest().body("id invalide ou n'existe pas");
            return null;
        }
    }

    @GetMapping("/9boxClass/{id}")
    @ResponseBody
    public TalentReview Classification(@PathVariable("id") long IdAgent) {

        TalentReview user = agentService.GetClassification9box( IdAgent);
        return user;
    }

    @GetMapping("/Agents9box/{class}")
    @ResponseBody
    public List<User> getAgentsClasse(@PathVariable("class") TalentReview talent) {
        List<User> list= userRepository.findByClassification(talent);
        return list;
    }

}
