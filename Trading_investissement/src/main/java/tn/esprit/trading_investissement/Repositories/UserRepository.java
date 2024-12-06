package tn.esprit.trading_investissement.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.trading_investissement.Entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
