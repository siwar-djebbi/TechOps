package tn.esprit.se.pispring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.se.pispring.entities.CustomerTracking;

public interface CustomerTrackingRepository extends JpaRepository<CustomerTracking,Long> {
}
