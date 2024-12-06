package tn.esprit.trading_investissement.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.trading_investissement.Dto.ActionDTO;
import tn.esprit.trading_investissement.Dto.RoomDTO;
import tn.esprit.trading_investissement.Entities.Action;
import tn.esprit.trading_investissement.Entities.Room;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.trading_investissement.Entities.User;
import tn.esprit.trading_investissement.Repositories.ActionRepository;
import tn.esprit.trading_investissement.Repositories.RoomRepository;
import tn.esprit.trading_investissement.Repositories.UserRepository;
import tn.esprit.trading_investissement.Services.RoomService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ActionRepository actionRepository;

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room, @RequestParam Long userId) {
        // Récupérez l'utilisateur
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("Utilisateur introuvable.");
        }
        room.setCreator(user.get());
        Room createdRoom = roomRepository.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }



    @GetMapping("/by-user/{userId}")
    public List<Room> getRoomsByUser(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("Utilisateur introuvable.");
        }
        return user.get().getRooms();
    }


    @GetMapping("/rooms")
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(room -> new RoomDTO(
                        room.getId(),
                        room.getName(),
                        room.getCreator() != null ? room.getCreator().getUsername() : "Unknown" // Vérifie si le créateur existe
                ))
                .collect(Collectors.toList());
    }



    @PostMapping("/rooms/{roomId}/actions")
    public ResponseEntity<String> saveRoomAction(@PathVariable Long roomId, @RequestBody ActionDTO action) {
        // Logique pour enregistrer l'action
        roomService.saveAction(roomId, action);
        return ResponseEntity.ok("Action saved successfully");
    }

//    @GetMapping("/rooms/{roomId}/actions")
//    public ResponseEntity<List<Action>> getRoomActions(@PathVariable Long roomId) {
//        Room room = roomRepository.findById(roomId)
//                .orElseThrow(() -> new RuntimeException("Room not found"));
//
//        List<Action> actions = actionRepository.findByRoom(room);
//        return ResponseEntity.ok(actions);
//    }
@GetMapping("/{roomId}/actions")
public ResponseEntity<List<ActionDTO>> getRoomActions(@PathVariable Long roomId) {
    System.out.println("Fetching actions for room ID: " + roomId);
    List<ActionDTO> actions = roomService.getRoomActions(roomId);
    return ResponseEntity.ok(actions);
}




    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        return room.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }




}



