package tn.esprit.mfb.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Repository.UserRepository;
import tn.esprit.mfb.Services.EmailSenderService;
import tn.esprit.mfb.Services.UserService;
import tn.esprit.mfb.config.JwtBlacklistService;
import tn.esprit.mfb.config.JwtService;
import tn.esprit.mfb.entity.Fund;
import tn.esprit.mfb.entity.User;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@CrossOrigin("*")
public class UserControlleur {

    private final UserService userService;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final JwtBlacklistService jwtBlacklistService;

    @PostMapping("/restpassword/request")
    public ResponseEntity<String> requestPasswordReset(@RequestBody Map<String, String> email) {
        String userEmail = email.get("email");
        userService.requestPasswordReset(userEmail);
        return ResponseEntity.ok("Password reset requested successfully.");
    }

    @PostMapping("/validate-token/{useremail}")
    public ResponseEntity<String> validateResetToken(@PathVariable("useremail") String email, @RequestBody Map<String, String> codeMap) {
        try {
            String userEmail = email;
            var user = userRepository.findByEmail(userEmail);
            Integer userCode = user.getCode();

            // Récupérer le code de la map
            String code = codeMap.get("code");

            System.out.println("BLACK LISTAAAAA ");
            System.out.println(jwtBlacklistService.isTokenBlacklisted(String.valueOf(userCode)));

            // Vérifier si le code de l'utilisateur correspond au code de la requête et s'il n'est pas en liste noire
            if (userCode.equals(Integer.valueOf(code)) && !jwtBlacklistService.isTokenBlacklisted(String.valueOf(userCode))) {
                String passwordResetUrl = "http://localhost:8084/api/user/new-password/" + userEmail;
                emailSenderService.sendSimpleEmail(userEmail, "Password reset link", passwordResetUrl);
                jwtBlacklistService.blacklistCode(userCode);

                return ResponseEntity.ok("Code is valid. Password reset link sent to your email.");
            } else {
                return ResponseEntity.badRequest().body("Invalid or expired code.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the request.");
        }
    }


    @PutMapping("/new-password/{useremail}")
    public ResponseEntity<String> resetPassword(@PathVariable("useremail") String userEmail, @RequestBody Map<String, String> password) {
        String pwd = password.get("password");

        return userService.newpassword(userEmail,pwd);

    }

    @PutMapping("/updateProfil")
    public User modifyFund(@RequestBody User u,@PathVariable("useremail") String userEmail) {
        return userService.updateUser(u,userEmail);
    }

}

