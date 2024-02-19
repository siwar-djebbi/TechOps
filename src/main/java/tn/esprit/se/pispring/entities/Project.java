package tn.esprit.se.pispring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long project_id;
    private String project_name;
    @Temporal(TemporalType.DATE)
    private Date project_startdate;
    @Temporal(TemporalType.DATE)
    private Date project_enddate;
    private String project_description;
    private String project_manager;
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="project")
    private Set<Task> Tasks;
    @OneToOne
    private Budget budget;



}
