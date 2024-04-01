package tn.esprit.se.pispring.entities;

import javax.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Resources {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long resource_id;
    private String resource_name;
    private String resources_description;
    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;
    private Float resources_cost;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "task_resourcess",
            joinColumns = @JoinColumn(name = "resourcess_resource_id"),
            inverseJoinColumns = @JoinColumn(name = "tasks_task_id"))
    private Set<Task> tasks;
}
