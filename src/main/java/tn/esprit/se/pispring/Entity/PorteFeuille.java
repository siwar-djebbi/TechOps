package tn.esprit.se.pispring.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PorteFeuille {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idPortef;
    private String name;
    private String descreption;

    @OneToOne(mappedBy="portefeuille")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Consultant> consultants;

    @OneToOne
    private CostemerTracking costemertracking;
}
