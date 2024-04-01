package tn.esprit.se.pispring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.se.pispring.entities.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

}
