package tn.esprit.se.pispring.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmployeeDetaile {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idEmp;
    private String Salary ;
    private Date DateEmbouche ;
    private Date DateNaiss ;
    private String EtatSocial ;
    private String rating;

    @OneToOne(mappedBy="employeedetaile")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="employeedetaile")
    private Set<Performance> Performances;
}
