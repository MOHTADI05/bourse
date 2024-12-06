package tn.esprit.trading_investissement.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.trading_investissement.Entities.Action;
import tn.esprit.trading_investissement.Entities.Room;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Long> {

    List<Action> findByRoom(Room room);

}
