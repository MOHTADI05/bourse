package tn.esprit.trading_investissement.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.trading_investissement.Dto.ActionDTO;
import tn.esprit.trading_investissement.Entities.Action;
import tn.esprit.trading_investissement.Entities.Room;
import tn.esprit.trading_investissement.Entities.User;
import tn.esprit.trading_investissement.Repositories.ActionRepository;
import tn.esprit.trading_investissement.Repositories.RoomRepository;
import tn.esprit.trading_investissement.Repositories.UserRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomService {

    UserRepository userRepository;

    @Autowired
    private ActionRepository actionRepository;


    @Autowired
    private RoomRepository roomRepository;


    public void saveAction(Long roomId, ActionDTO actionDTO) {
        // Fetch the room
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Create and populate the Action entity
        Action action = new Action();
        action.setDescription(actionDTO.getDescription());
        action.setTimestamp(actionDTO.getTimestamp());
        action.setRoom(room);

        // Save the action
        actionRepository.save(action);
    }

    public Room createRoom(String name, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Room room = new Room();
        room.setName(name);
        room.setCreator(user);
        return roomRepository.save(room);
    }

//    public List<Action> getRoomActions(Long roomId) {
//        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
//        return actionRepository.findByRoom(room);
//    }
public List<ActionDTO> getRoomActions(Long roomId) {
    Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new RuntimeException("Room not found"));

    return actionRepository.findByRoom(room).stream()
            .map(action -> new ActionDTO(action.getDescription(), action.getTimestamp()))
            .collect(Collectors.toList());
}







}
