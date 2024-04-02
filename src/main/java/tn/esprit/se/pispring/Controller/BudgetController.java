package tn.esprit.se.pispring.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.se.pispring.Service.IBudegetService;
import tn.esprit.se.pispring.entities.Budget;


import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/Budget")
public class BudgetController {
    IBudegetService iBudegetService;


   @PostMapping("/addBudget")
    public Budget addBudget(@RequestBody Budget budget) {
        Budget budget1 = iBudegetService.addBudget(budget);
        return  budget1;
    }
    @PutMapping("/update-budget")
    public Budget updateBudget(@RequestBody Budget p) {
        Budget budget = iBudegetService.updateBudget(p);
        return budget;
    }
    @DeleteMapping("/remove-budget/{budget_id}")
    public void removeBudget(@PathVariable("budget_id") Long budget_id) {
      iBudegetService.deleteBudget(budget_id);
    }
    @GetMapping("/retrieve-all-budget")
    public List<Budget> getAllBudget() {
        List<Budget> budgets = iBudegetService.getAllBudget();
        return budgets;
    }
    @GetMapping("/retrieve-budget/{budget_id}")
    public  Budget retrievebudget(@PathVariable("budget_id") Long budget_id) {
        return iBudegetService.getBudget(budget_id);
    }

    @PostMapping("/assign/{budget_id}/toProject/{projectId}")
    public Budget assignBudgetToProject(@PathVariable Long budget_id, @PathVariable Long projectId) {
        return iBudegetService.assignBudgetToProject(budget_id,projectId);
    }
}
