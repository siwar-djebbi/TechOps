package tn.esprit.se.pispring.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Consultant {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idCon;
    private String FirtName ;
    private String LastName ;
    private String Email;
    private String password;
    private String adress;
    private int telephone;

    @ManyToMany(mappedBy="consultants", cascade = CascadeType.ALL)
    private Set<PorteFeuille> portefeuilles;



}
