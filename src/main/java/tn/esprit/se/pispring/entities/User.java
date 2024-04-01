package tn.esprit.se.pispring.entities;

import javax.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String firstName ;
    private String lastName ;
    private String email;
    private String password;
    private Integer telephone;


    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns  = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    private Boolean connected = false;
    private boolean deleted = false;


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
    private Set<Leav> Leaves;

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
