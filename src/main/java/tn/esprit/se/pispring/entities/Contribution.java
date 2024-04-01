package tn.esprit.se.pispring.entities;

import javax.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contribution {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long contribution_id;
    private String contribution_designation;
    private Float contribution_amount;
    private String contribution_month;
    @ManyToOne
    User user;


}
