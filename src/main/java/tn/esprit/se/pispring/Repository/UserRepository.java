package tn.esprit.se.pispring.Repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.se.pispring.entities.User;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findOneByEmailAndPassword(String email, String password);
    User findByEmail(String email);

    User findByFirstName(String username);

    @Transactional
    Long deleteByFirstName(String username);
}
