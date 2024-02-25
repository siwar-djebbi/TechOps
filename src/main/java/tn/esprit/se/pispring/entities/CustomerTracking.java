package tn.esprit.se.pispring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomerTracking {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long history_id;
    private String history_description;
    @Temporal(TemporalType.DATE)
    private Date meet_date;
    private String meet_duration ;
    private String meet_subject ;
    private String meet_participant ;
    private String meet_objective ;
    private String next_step ;

    @OneToOne(mappedBy="customertracking")
    private User user;

}
