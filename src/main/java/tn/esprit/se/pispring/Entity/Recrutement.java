package tn.esprit.se.pispring.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recrutement {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idOffre;
    private String Post ;
    private String Description;
    private String Requirements;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    User user;
}
