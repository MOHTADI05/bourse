package tn.esprit.mfb.Services;

import org.springframework.http.ResponseEntity;
import tn.esprit.mfb.controller.AuthenticationResponse;
import tn.esprit.mfb.entity.User;

import java.util.List;

public interface UserService {


    void DeleteUser(User u);
    void DeleteUser(Long id);
    User updateUser(User u, String userEmail);

    List<User> getAllUser();
    User GetUser(String userEmail);
    AuthenticationResponse register(User u);
    AuthenticationResponse authenticate(User u);
    ResponseEntity<String> logout(String token);
    void BlockUser(Long id);
    void UnblockUser(Long id);
    void requestPasswordReset(String userEmail);
    ResponseEntity<String> newpassword(String userEmail, String password);





}
