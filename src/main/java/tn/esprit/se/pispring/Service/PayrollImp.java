package tn.esprit.se.pispring.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.Repository.PayrollRepository;
import tn.esprit.se.pispring.Repository.UserRepository;
import tn.esprit.se.pispring.entities.Payroll;
import tn.esprit.se.pispring.entities.PayrollConfig;
import tn.esprit.se.pispring.entities.Prime;
import tn.esprit.se.pispring.entities.User;

import java.util.List;
@Service
@AllArgsConstructor
@Slf4j
public class PayrollImp implements PayrollService {
    PayrollRepository payrollRepository;
    UserRepository userRepository;
    ContributionService contributionService;
    PrimeService primeService;
    PayrollConfigService payrollConfigService;


    @Override
    public List<Payroll> retrieveAllPayrolls() {
        return payrollRepository.findAll();
    }

    @Override
    public Payroll addPayroll(Payroll payroll) {
        //Double netSalary = calculateNetSalary(payroll.getBrut_salary(), payroll.getWork_hours_number());
        //payroll.setNet_salary(netSalary.floatValue());
        return payrollRepository.save(payroll);
    }
    @Override
    public Payroll updatePayroll(Payroll payroll,  Long idpayroll)
    {
        Payroll p = payrollRepository.findById(idpayroll).get();
        if (p != null){
            payroll.setPayroll_id(idpayroll);
            return payrollRepository.save(payroll);
        }
        return null;
    }

    @Override
    public Payroll retrievePayroll(Long idPayroll) {
        return payrollRepository.findById(idPayroll).get();
    }

    @Override
    public void removePayroll(Long idPayroll) {
        payrollRepository.deleteById(idPayroll);
    }

    public Payroll affectPayrollUser(Payroll payroll, Long userId) {
        User user = userRepository.findById(userId).get();
        Float prime = primeService.getSumAmountForUserMonthYear(userId,payroll.getMonth(), payroll.getYear());
        Float contrib = contributionService.getSumAmountForUserMonthYear(userId,payroll.getMonth(), payroll.getYear());
        Double netSalary = calculateNetSalary(payroll.getBrut_salary(),payroll.getWork_hours_number(),prime, contrib);
        payroll.setNet_salary(netSalary.floatValue());
        payroll.setUser(user);
        payrollRepository.save(payroll);
        return payroll;
    }
    private Double calculateNetSalary(float brutSalary, int totalHoursWorked, float prime, float deductions){
        PayrollConfig payrollConfig = payrollConfigService.retrievePayrollConfig(1L);
        // Calcul du salaire horaire
        float dailyRate = brutSalary / payrollConfig.getMonth_days(); // 22 jours de travail par mois, 8 heures par jour
        // Calcul du salaire brut pour le mois entier
        float monthlySalary = totalHoursWorked * dailyRate;
        // Ajout des primes
        monthlySalary += prime;
        // Soustraction des cotisations
        monthlySalary -= deductions;
        // Calcul du salaire net
        double netSalary = monthlySalary * payrollConfig.getFees_rate(); // 22% de déduction pour les cotisations
        return netSalary;
    }

    public List<Object[]> getPayrollDetailsForYear(Integer year) {
        return payrollRepository.getPayrollDetailsForYear(year);
    }
}

