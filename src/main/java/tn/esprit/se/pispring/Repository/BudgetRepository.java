package tn.esprit.se.pispring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.se.pispring.entities.Budget;
import tn.esprit.se.pispring.entities.Project;

public interface BudgetRepository extends JpaRepository<Budget,Long> {
}
