package tn.esprit.se.pispring.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Performance {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idPerf;
    private String Salary ;
    private Date DateEvaluation ;
    private String Comment ;
    private int Score ;

    @ManyToOne
    EmployeeDetaile employeedetaile;

    @ManyToOne
    Paie paie;
}
