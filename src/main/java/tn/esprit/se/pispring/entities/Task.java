package tn.esprit.se.pispring.entities;

import javax.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Builder
@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long task_id;
    private String task_name;
    @Temporal(TemporalType.DATE)
    private Date task_startdate;
    @Temporal(TemporalType.DATE)
    private Date task_enddate;
    private String task_description;
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Resources> resourcess;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> users;
    @ManyToOne
    Project project;

}
