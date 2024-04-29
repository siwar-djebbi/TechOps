package tn.esprit.se.pispring.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import tn.esprit.se.pispring.Repository.ProjectRepository;
import tn.esprit.se.pispring.Repository.TaskRepository;
import tn.esprit.se.pispring.Repository.UserRepository;
import tn.esprit.se.pispring.entities.Project;
import tn.esprit.se.pispring.entities.ProjectStatus;
import tn.esprit.se.pispring.entities.Resources;
import tn.esprit.se.pispring.entities.Task;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProjectService implements IProjectService{
    ProjectRepository projectRepository;
    TaskRepository taskRepository;



    @Autowired
    private UserRepository userRepository;





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





   @Override
   public List<Project> getCompletedProjects() {
       List<Project> completedProjects = projectRepository.findByProjectStatus(ProjectStatus.COMPLETED);
       Date currentDate = new Date();
       completedProjects = completedProjects.stream()
               .filter(project -> project.getProjectEnddate().after(currentDate))
               .collect(Collectors.toList());
       return completedProjects;
   }







    @Override
    public Date findLatestTaskEndDate(Project project) {
        Date latestTaskEndDate = null;
        // Assurez-vous que la méthode getTasks() renvoie la liste des tâches associées au projet
        for (Task task : project.getTasks()) {
            if (latestTaskEndDate == null || task.getTaskEnddate().after(latestTaskEndDate)) {
                latestTaskEndDate = task.getTaskEnddate();
            }
        }
        return latestTaskEndDate;
    }

    @Transactional
    @Override
    public void updateAllProjectEndDates() {
        List<Project> projects = projectRepository.findAll();

        for (Project project : projects) {
            Date latestTaskEndDate = findLatestTaskEndDate(project);
            if (latestTaskEndDate != null && latestTaskEndDate.after(project.getProjectEnddate())) {
                project.setProjectEnddate(latestTaskEndDate);
                projectRepository.save(project);
            }
        }
    }

}
