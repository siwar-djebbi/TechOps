package tn.esprit.se.pispring.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.Repository.BudgetRepository;
import tn.esprit.se.pispring.Repository.ProjectRepository;
import tn.esprit.se.pispring.entities.Budget;
import tn.esprit.se.pispring.entities.Project;

import java.util.List;
@Service
@Slf4j
@AllArgsConstructor
public class BudgetService implements IBudegetService{
    BudgetRepository budgetRepository;
    ProjectRepository projectRepository;

    @Override
    public Budget addBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    @Override
    public Budget updateBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    @Override
    public Budget getBudget(Long budget_id) {
        return budgetRepository.findById(budget_id).get();
    }

    @Override
    public List<Budget> getAllBudget() {
        return budgetRepository.findAll();
    }
    @Override
    public void deleteBudget(Long budget_id) {
        budgetRepository.deleteById(budget_id);
    }


    @Override
    public Budget assignBudgetToProject(Long budget_id, Long projectId) {
        Budget budget = budgetRepository.findById(budget_id).orElse(null);
        Project project = projectRepository.findById(projectId).orElse(null);

        if (budget != null && project != null) {
            project.setBudget(budget);
            projectRepository.save(project);
            return budget;
        } else {
            return null;
        }
    }
}
