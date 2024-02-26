package tn.esprit.se.pispring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.se.pispring.entities.Consultant;

public interface ConsultantRepository extends JpaRepository<Consultant,Long> {
}
