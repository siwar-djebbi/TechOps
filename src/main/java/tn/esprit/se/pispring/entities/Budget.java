package tn.esprit.se.pispring.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long budget_id;
    private Float budget_amount;
    private String dependencies;
    @OneToOne(mappedBy="budget")
    private Project project;



}
