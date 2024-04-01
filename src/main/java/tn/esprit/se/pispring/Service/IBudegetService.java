package tn.esprit.se.pispring.Service;

import tn.esprit.se.pispring.entities.Budget;
import tn.esprit.se.pispring.entities.Project;

import java.util.List;

public interface IBudegetService {
    Budget addBudget(Budget budget);
    Budget updateBudget(Budget budget);
    void deleteBudget(Long budget_id);
    Budget getBudget(Long budget_id);
    public List<Budget> getAllBudget();

    Budget assignBudgetToProject(Long budget_id, Long projectId);
}
