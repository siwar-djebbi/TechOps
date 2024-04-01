package tn.esprit.se.pispring.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.se.pispring.entities.Payroll;
import tn.esprit.se.pispring.entities.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface PayrollRepository extends JpaRepository<Payroll,Long> {
    @Query("SELECT p.user.firstName,p.user.lastName, p.brut_salary, p.net_salary, p.month, p.year FROM Payroll p WHERE p.year = :year")
    List<Object[]> getPayrollDetailsForYear(@Param("year") Integer year);

}
