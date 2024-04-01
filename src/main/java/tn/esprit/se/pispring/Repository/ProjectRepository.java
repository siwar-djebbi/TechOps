package tn.esprit.se.pispring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.se.pispring.entities.Project;
import tn.esprit.se.pispring.entities.Task;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    @Query("SELECT p FROM Project p WHERE p.project_manager = :projectManager")
    List<Project> findByProjectManager(@Param("projectManager") String projectManager);

}
