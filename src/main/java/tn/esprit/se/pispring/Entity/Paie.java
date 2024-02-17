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
public class Paie {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idPaie;
    private String Bulletin ;
    private Date payementPeriode ;
    private int Amount ;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="paie")
    private Set<Performance> Performances;


}
