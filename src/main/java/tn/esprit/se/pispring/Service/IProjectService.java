package tn.esprit.se.pispring.Service;

import tn.esprit.se.pispring.entities.Project;
import tn.esprit.se.pispring.entities.ProjectStatus;

import java.util.Date;
import java.util.List;

public interface IProjectService {
    Project addProject(Project project);
    Project updateProject(Project project);
    void deleteProject(Long projectId);
    Project getProject(Long projectId);
    public List<Project> getAllProject();
    List<Project> getDelayedProjects();


    List<Project> getCompletedProjects();
}
