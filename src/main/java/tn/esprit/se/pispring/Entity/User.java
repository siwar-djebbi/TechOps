package tn.esprit.se.pispring.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String FirtName ;
    private String LastName ;
    private String Email;
    private String password;
    private int telephone;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Boolean connected = false;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<Recrutement> Recrutements;

    @OneToOne
    private EmployeeDetaile employeedetaile;

    @ManyToOne
    Leave leave;

    @OneToOne
    private PorteFeuille portefeuille;

}

