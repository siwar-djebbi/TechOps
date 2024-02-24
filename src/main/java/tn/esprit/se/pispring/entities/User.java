package tn.esprit.se.pispring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
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
    private String firstName ;
    private String lastName ;
    private String email;
    private String password;
    private Integer telephone;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Boolean connected = false;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<Recruitment> Recruitments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<Performance> Performances;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<Payroll> Payrolls;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<Prime> Primes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<Contribution> Contributions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<Leave> Leaves;

    //@ManyToMany(mappedBy="users", cascade = CascadeType.ALL)
   // private Set<Portfolio> portfolios;

    @ManyToMany(mappedBy="users", cascade = CascadeType.ALL)
    private Set<Task> tasks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<Command> Commands;

    //@OneToOne(mappedBy="portfolio")
    //private Consultant consultant;

    @ManyToOne
    Portfolio portfolio;

    @OneToOne
    private CustomerTracking customertracking;
}
