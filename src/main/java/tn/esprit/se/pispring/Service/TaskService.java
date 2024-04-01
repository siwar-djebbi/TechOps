package tn.esprit.se.pispring.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.Repository.ProjectRepository;
import tn.esprit.se.pispring.Repository.TaskRepository;
import tn.esprit.se.pispring.Repository.UserRepository;
import tn.esprit.se.pispring.entities.*;

import javax.transaction.Transactional;
import java.util.*;


@Service
@Slf4j
@AllArgsConstructor
public class TaskService implements ITaskService{
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectService projectService;
    @Override
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long task_id) {
        taskRepository.deleteById(task_id);
    }

    @Override
    public Task getTask(Long task_id) {
        return taskRepository.findById(task_id).get();
    }

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }
    @Override
    public List<Task> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectProjectId(projectId);
    }
    @Override
    public Task assignTaskToProject(Long task_id , Long projectId) {
        Task task = taskRepository.findById(task_id).orElse(null);
        Project project = projectRepository.findById(projectId).orElse(null);
        if (task != null && project != null) {
            task.setProject(project);
            return taskRepository.save(task);
        }
        return null;
    }
    @Transactional
    @Override
    public Task assignTaskToUser(Long id, Long task_id) {
        Task task = taskRepository.findById(task_id).orElse(null);
        User user = userRepository.findById(id).orElse(null);
        if (task != null && user != null) {
            task.getUsers().add(user);
            return taskRepository.save(task);
        }
        return null;
    }
    @Override
    public Set<User> getProjectTeam(Long projectId) {
        Set<User> users= new HashSet<>();
        taskRepository.findTasksByProjectProjectId(projectId).forEach(task->users.addAll(task.getUsers()));
        return users;
    }
   /* public Set<User> getProjectTeam(Long projectId) {
        Set<User> projectTeam = new HashSet<>();

        List<Task> projectTasks = taskRepository.findByProjectProjectId(projectId);
        for (Task task : projectTasks) {
            projectTeam.addAll(task.getUsers());
        }

        return projectTeam;
    }*/
   @Override
    public List<Task> getCompletedTasksByProject(Long projectId) {
        return taskRepository.findTasksByProjectProjectIdAndTaskStatus(projectId, TaskStatus.COMPLETED);
    }


    @Override
    public double calculateTaskBudget(Task task, Set<User> users) {
        double taskBudget = 0.0;

        for (User user : users) {
            User userWithSalary = userRepository.findById(user.getId()).orElse(null);
            if (userWithSalary != null) {
                taskBudget += userWithSalary.getSalaire();
            }
        }
        return taskBudget;
    }
   /* @Override
    public double calculateProjectBudget(Long projectId) {
        double totalBudget = 0.0;
        List<Task> tasks = taskRepository.findByProjectProjectId(projectId);
        for (Task task : tasks) {
            Set<User> users = task.getUsers();
            double taskBudget = calculateTaskBudget(task, users);
            totalBudget += taskBudget;
        }
        return totalBudget;
    }*/

    @Override
    public double calculateProjectBudget(Long projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) {
            return 0.0; // Gérer le cas où le projet n'est pas trouvé
        }

        // Calculer la durée du projet en mois
        int projectDurationInMonths = calculateDurationInMonths(project.getProject_startdate(), project.getProjectEnddate());

        // Calculer le salaire total de l'équipe pour un mois
        Set<User> users = getProjectTeam(projectId);
        double teamSalaryCostPerMonth = 0.0;
        for (User user : users) {
            teamSalaryCostPerMonth += user.getSalaire();
        }

        // Calculer le budget total en multipliant le salaire total par la durée du projet en mois
        double totalBudget = teamSalaryCostPerMonth * projectDurationInMonths;

        // Ajouter les coûts fixes des ressources si nécessaire
        double fixedResourceCost = 10000.0;
        totalBudget += fixedResourceCost;

        return totalBudget;
    }
    private int calculateDurationInMonths(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int diffYear = endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + endCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH);

        return diffMonth;
    }


    @Override
    public double calculateCompletionPercentage(Long projectId) {
        List<Task> allTasks = taskRepository.findTasksByProjectProjectId(projectId);
        int totalTasks = allTasks.size();
        List<Task> completedTasks = taskRepository.findTasksByProjectProjectIdAndTaskStatus(projectId, TaskStatus.COMPLETED);
        int completedCount = completedTasks.size();
        if (totalTasks == 0) {
            return 0.0;
        } else {
            return ((double) completedCount / totalTasks) * 100;
        }
    }

    @Override
    public Map<TaskStatus, Long> getTaskStatusDistribution(Long projectId) {
        List<Task> tasks = taskRepository.findTasksByProjectProjectId(projectId);
        Map<TaskStatus, Long> taskStatusDistribution = new HashMap<>();
        for (TaskStatus status : TaskStatus.values()) {
            long count = tasks.stream().filter(task -> task.getTaskStatus() == status).count();
            taskStatusDistribution.put(status, count);
        }
        return taskStatusDistribution;
    }
    @Override
    public Map<TaskStatus, Double> getTaskStatusPercentage(Long projectId) {
        List<Task> tasks = taskRepository.findTasksByProjectProjectId(projectId);
        Map<TaskStatus, Double> taskStatusPercentage = new HashMap<>();
        int totalTasks = tasks.size();

        for (TaskStatus status : TaskStatus.values()) {
            long count = tasks.stream().filter(task -> task.getTaskStatus() == status).count();
            double percentage = (count / (double) totalTasks) * 100;
            taskStatusPercentage.put(status, percentage);
        }

        return taskStatusPercentage;
    }

    @Override
    public List<Task> findTasksCompletedBeforeEndDate(Date endDate) {
        return taskRepository.findByTaskEnddateBeforeAndTaskStatus(endDate, TaskStatus.COMPLETED);
    }
    @Override
    public List<Task> findTasksCompletedLate() {
        Date currentDate = new Date();
        return taskRepository.findByTaskEnddateBeforeAndTaskStatusNot(currentDate, TaskStatus.COMPLETED);
    }
///////////////
    /// @Override
    //    public double calculateCompletionPercentage(Long projectId) {
    //        List<Task> tasks = taskRepository.findTasksByProjectProjectId(projectId);
    //        if (tasks.isEmpty()) {
    //            return 0.0;
    //        }
    //        long completedTasks = tasks.stream().filter(task -> task.getTaskStatus() == TaskStatus.COMPLETED).count();
    //        return ((double) completedTasks / tasks.size()) * 100;
    //    }
    //}




    ///////////
    // public double calculateActualCostForTask(Long taskId) {

    //        Task task = taskRepository.findById(taskId).orElse(null);
    //        if (task == null) {
    //            return 0.0;
    //        }
    //
    //        double totalCost = 0.0;
    //        for (User user : task.getUsers()) {
    //            // Supposons que chaque utilisateur a un taux horaire associé
    //            double hourlyRate = user.getHourlyRate();
    //            // Supposons que les heures travaillées par chaque utilisateur sont suivies
    //            double hoursWorked = user.getHoursWorkedOnTask(taskId);
    //            totalCost += hourlyRate * hoursWorked;
    //        }
    //
    //        // Ajoutez d'autres coûts fixes si nécessaire
    //        totalCost += task.getFixedCosts();
    //
    //        return totalCost;
    //    }
    //public double getHoursWorkedOnTask(Long taskId) {
        // Récupérer la tâche spécifique à partir de son identifiant
       // Task task = taskRepository.findById(taskId).orElse(null);

        //double totalHoursWorked = 0.0;

        // Vérifier si la tâche existe et si elle a des utilisateurs associés
        //if (task != null && task.getUsers() != null) {
            // Parcourir les utilisateurs associés à la tâche
         //   for (User user : task.getUsers()) {
                // Ajouter les heures travaillées par cet utilisateur sur cette tâche
              //  totalHoursWorked += user.getHoursWorkedOnTask(taskId);
           // }
     //   }

        //return totalHoursWorked;
    //}
    ////////
}