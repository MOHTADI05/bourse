package tn.esprit.mfb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Repository.UserRepository;
import tn.esprit.mfb.Services.ClusteringService;
import tn.esprit.mfb.Services.UserService;
import tn.esprit.mfb.config.JwtBlacklistService;
import tn.esprit.mfb.config.JwtService;
import tn.esprit.mfb.entity.User;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/user/auth")
@RequiredArgsConstructor
public class AuthenficationController {

    private final UserService service;

    private final UserRepository Userrepository;

    private final ClusteringService clusteringService;

    private final JwtService jwtService;
    private AuthenticationResponse jwt;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User user) {

        return ResponseEntity.ok(service.register(user));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody User user) {
    System.out.println(user.isIsbloked());


            AuthenticationResponse authenticationResponse = service.authenticate(user);
            String jwtToken = authenticationResponse.getToken();

        return ResponseEntity.ok(service.authenticate(user));

    }
    @GetMapping("/logout/{jwtToken}")
    public ResponseEntity<String> logout(@PathVariable("jwtToken") String jwtToken) throws JsonProcessingException {

        ResponseEntity<String> response = service.logout(jwtToken);
        String token = String.valueOf(jwtToken);

        // Convertir le rôle en JSON
        ObjectMapper mapper = new ObjectMapper();
        String jsontoken = mapper.writeValueAsString(token);
        return ResponseEntity.ok(jsontoken);
    }
    @GetMapping("/cluster")
    public void cluster() throws Exception {

        clusteringService.segmentation();

    }
    @GetMapping("/segmentation")
    public ResponseEntity<double[]> segmentation() {
        try {
            // Appel de la fonction segmentation() du service
            double[] percentages = clusteringService.segmentation2();
            return ResponseEntity.ok(percentages);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/userRole/{jwtToken}")
    public ResponseEntity<String> utilisateurRole(@PathVariable("jwtToken") String jwtToken) {
        try {
            String email = jwtService.extractUsername(jwtToken);
            User u = Userrepository.findByEmail(email);
            String role = String.valueOf(u.getRole());

            // Convertir le rôle en JSON
            ObjectMapper mapper = new ObjectMapper();
            String jsonRole = mapper.writeValueAsString(role);

            // Retourner le rôle sous forme JSON avec le code de statut 200 (OK)
            return ResponseEntity.ok(jsonRole);
        } catch (Exception e) {
            // En cas d'erreur, retourner une réponse avec le code de statut 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la récupération du rôle de l'utilisateur.");
        }
    }

    @GetMapping("/cluster2")
    public ResponseEntity<List<Double>> getCluster2Data() {
        try {
            return ResponseEntity.ok(clusteringService.cluster2());
        } catch (Exception e) {
            // Gérer les exceptions ici
            e.printStackTrace();
            return null; // Ou renvoyer un message d'erreur approprié
        }
    }
        @GetMapping("/cluster1")
        public ResponseEntity<List<Double>> getCluster1Data() {
            try {
                return ResponseEntity.ok(clusteringService.cluster1());
            } catch (Exception e) {
                // Gérer les exceptions ici
                e.printStackTrace();
                return null; // Ou renvoyer un message d'erreur approprié
            }
        }

    @GetMapping("/userbytoken/{jwtToken}")
    public ResponseEntity<User> utilisateurbytoken(@PathVariable("jwtToken") String jwtToken) {
        try {
            String email = jwtService.extractUsername(jwtToken);
            User u = Userrepository.findByEmail(email);if (u != null) {
                 return ResponseEntity.ok(u);
            } else {
                 return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
             return ResponseEntity.status(500).build();
        }
    }
    @GetMapping("/userbymail/{email}")
    public ResponseEntity<User> userbymail(@PathVariable("email") String email) {
        try {
            User u = Userrepository.findByEmail(email);if (u != null) {
                return ResponseEntity.ok(u);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    @GetMapping("/userid/{jwtToken}")
    public ResponseEntity<Long> utilisateurid(@PathVariable("jwtToken") String jwtToken) {
        try {
            String email = jwtService.extractUsername(jwtToken);
            User u = Userrepository.findByEmail(email);
            Long id = u.getCin();
            return ResponseEntity.ok(id);
        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}



