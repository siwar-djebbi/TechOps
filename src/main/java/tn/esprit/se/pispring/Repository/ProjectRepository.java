package tn.esprit.se.pispring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.se.pispring.entities.Project;
import tn.esprit.se.pispring.entities.ProjectStatus;
import tn.esprit.se.pispring.entities.User;

import java.util.Date;
import java.util.List;
@Repository

public interface ProjectRepository extends JpaRepository<Project,Long> {
   // Project delete(Long projectId);
   List<Project> findByProjectEnddateBeforeAndProjectStatusNot(Date projectEnddate, ProjectStatus projectStatus);
   List<Project> findByProjectEnddateBeforeAndProjectStatusNotAndProjectEnddateBefore(Date enddate, ProjectStatus status, Date estimatedEnddate);
   List<Project> findByProjectStatus(ProjectStatus status);

   List<Project> findByProjectManager(String potfolioManager);

   int countByProjectManager(User currentUser);

   int countByProjectManagerAndProjectStatus(User currentUser, ProjectStatus projectStatus);


}
