package tn.esprit.se.pispring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.se.pispring.entities.Project;
import tn.esprit.se.pispring.entities.ProjectStatus;

import java.util.Date;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
   // Project delete(Long projectId);
   List<Project> findByProjectEnddateBeforeAndProjectStatusNot(Date projectEnddate, ProjectStatus projectStatus);
   List<Project> findByProjectEnddateBeforeAndProjectStatusNotAndProjectEnddateBefore(Date enddate, ProjectStatus status, Date estimatedEnddate);
   List<Project> findByProjectStatus(ProjectStatus status);
}
