package tn.esprit.mfb.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.UserRepository;
import tn.esprit.mfb.config.JwtBlacklistService;
import tn.esprit.mfb.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import tn.esprit.mfb.controller.AuthenticationResponse;
import tn.esprit.mfb.entity.TypeUser;
import tn.esprit.mfb.entity.User;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository Repuser;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtBlacklistService jwtBlacklistService;
    private final EmailSenderService emailSenderService;




    @Override
    public void DeleteUser(User u) {
        Repuser.delete(u);

    }
    @Override
    public  void DeleteUser(Long id){
            Repuser.deleteById(id);
    }

    @Override
    public User updateUser(User u,String userEmail ) {

        var user = Repuser.findByEmail(userEmail) ;
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Repuser.save(u);
        //var jwtToken = jwtService.generateToken( Repuser.save(u));
      return u;
    }


    @Override
    public List<User> getAllUser() {
        // Convertir l'itérable en liste
        Iterable<User> usersIterable = Repuser.findAll();
        List<User> usersList = new ArrayList<>();
        usersIterable.forEach(usersList::add);
        return usersList;
    }

    @Override
    public User GetUser(String userEmail) {
        var user = Repuser.findByEmail(userEmail);
        return user ;
    }

    @Override
    public AuthenticationResponse register(User u) {

        if (emailExists(u.getEmail())) {
            return AuthenticationResponse.builder()
                    .token(" error mail already used. ")
                    .build();
        }
        String encodedPassword = passwordEncoder.encode(u.getPassword());
        u.setPassword(encodedPassword);
        u.setRole(TypeUser.CLIENT);
        u.setIsbloked(false);
        var jwtToken = jwtService.generateToken( Repuser.save(u));
        emailSenderService.sendSimpleEmail("aissa.swiden@esprit.tn","new user","you have a new user his email is "+ u.getEmail());
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    private boolean emailExists(String email) {
        return Repuser.existsByEmail(email);
    }
    @Override
    public AuthenticationResponse authenticate(User u) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        u.getEmail(),
                        u.getPassword()

                )

        );
        System.out.println(u.getPassword());
        var user = Repuser.findByEmail(u.getEmail());
        if(user.isIsbloked()==true){
            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(jwtToken).build();

        }
        else{

            return AuthenticationResponse.builder()
                    .token("User is blocked. Please contact the administrator.")
                    .build();
        }
    }
    @Override
    public ResponseEntity<String> logout(String token) {

//System.out.println(u.getEmail());
//        var user = Repuser.findByEmail(u.getEmail())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        //String jwtToken = jwtService.extractUsername(token);
         jwtBlacklistService.blacklistToken(token);

        System.out.println(SecurityContextHolder.getContext());
        SecurityContextHolder.clearContext();

        System.out.println(SecurityContextHolder.getContext());
        // Répond avec un message de déconnexion réussie
        return ResponseEntity.status(HttpStatus.OK).body("Logged out successfully");
    }

    @Override
    public void BlockUser(Long id) {
        User u= Repuser.findByCin(id);
        u.setIsbloked(false);
        emailSenderService.sendSimpleEmail(u.getEmail(),"locked","your account is locked wait for unlocking or contact the admin  ");

        Repuser.save(u);

    }

    @Override
    public void UnblockUser(Long id) {
        User u= Repuser.findByCin(id);
        System.out.println(u);
        u.setIsbloked(true);
        emailSenderService.sendSimpleEmail(u.getEmail(),"unlocked","your account is unlocked ");

        Repuser.save(u);
    }
    @Override
    public void requestPasswordReset(String userEmail) {

        var user = Repuser.findByEmail(userEmail) ;
        if (user != null) {
            //String token = jwtService.generatePasswordResetToken(user);
            Integer code = generateRandomNumber();
            System.out.println("hetha fel GENEREAT"+ code);
            user.setCode(code);
            Repuser.save(user);
            System.out.println(user.getCode());
            // Envoyer l'e-mail avec le lien de réinitialisation
            String resetUrl = "http://localhost:8084/api/user/validate-token/"+userEmail;
//            String resetUrl = "http://localhost:8084/api/user/validate-token?token=" + code;
            sendResetEmail(userEmail, resetUrl,code);
        }
    }
    private void sendResetEmail(String userEmail, String resetUrl,Integer code) {
        emailSenderService.sendSimpleEmail(userEmail,"Demande de réinitialisation de mot de passe","Pour réinitialiser votre mot de passe, veuillez cliquer sur le lien suivant : " + resetUrl + " et voici votre code de verif : "+ code );
    }
    @Override
    public ResponseEntity<String> newpassword(String userEmail, String password){

        try {
            var user = Repuser.findByEmail(userEmail);
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
            Repuser.save(user);
//            var jwtToken = jwtService.generateToken( Repuser.save(user));
//            System.out.println(jwtToken);
            return ResponseEntity.ok("Password reset successful.");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error resetting password.");
        }
    }


        public static int generateRandomNumber() {

            Random random = new Random();
            int randomNumber = random.nextInt(90000) + 10000;
            return randomNumber;
        }


        private Date calculateExpiryDate() {
        // Calculer la date d'expiration (par exemple, dans 24 heures)
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }


}
