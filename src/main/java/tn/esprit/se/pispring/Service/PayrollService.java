package tn.esprit.se.pispring.Service;

import tn.esprit.se.pispring.entities.Payroll;


import java.util.List;

public interface PayrollService {
    List<Payroll> retrieveAllPayrolls();

    Payroll addPayroll(Payroll payroll);

    Payroll updatePayroll(Payroll payroll, Long idpayroll);

    Payroll retrievePayroll(Long idPayroll);
    public Payroll affectPayrollUser(Payroll payroll, Long userId);
    void removePayroll(Long idPayroll);
    //void generatePaymentOrder(Payroll payroll);


}
