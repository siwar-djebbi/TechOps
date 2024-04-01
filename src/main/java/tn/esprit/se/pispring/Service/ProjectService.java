package tn.esprit.se.pispring.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.Repository.ProjectRepository;
import tn.esprit.se.pispring.entities.Project;
import tn.esprit.se.pispring.entities.ProjectStatus;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProjectService implements IProjectService{
    ProjectRepository projectRepository;



    @Override
    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project) {
        return  projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    @Override
    public Project getProject(Long projectId) {
        return projectRepository.findById(projectId).get();
    }

    @Override
    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }


    @Override

        public List<Project> getDelayedProjects() {
            Date currentDate = new Date();
            ProjectStatus completedStatus = ProjectStatus.COMPLETED;
            return projectRepository.findByProjectEnddateBeforeAndProjectStatusNot(currentDate, completedStatus);
        }




    // @Override
  // public List<Project> getProjectsByEndDateAndStatus(Date endDate, ProjectStatus status) {
     //   Date currentDate = new Date();
     //   return projectRepository.findByProjectEnddateBeforeAndProjectStatusNotAndProjectEnddateBefore(currentDate, status, endDate);
   // }
   @Override
   public List<Project> getCompletedProjects() {
       List<Project> completedProjects = projectRepository.findByProjectStatus(ProjectStatus.COMPLETED);
       Date currentDate = new Date();
       completedProjects = completedProjects.stream()
               .filter(project -> project.getProjectEnddate().after(currentDate))
               .collect(Collectors.toList());
       return completedProjects;
   }


}
