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
@Table(name = "RECRUITMENT")
public class Recruitment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long offer_id;
    private String post_title;
    private String description;
    private String requirements;
    @Temporal(TemporalType.DATE)
    private Date recruitment_date;
    @Enumerated(EnumType.STRING)
    private Recruitment_Status recruitment_status;
    @ManyToOne
    User user;
}
