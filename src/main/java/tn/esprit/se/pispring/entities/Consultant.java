package tn.esprit.se.pispring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Consultant {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long consultant_id;
    private String consultant_firstname;
    private String consultant_lastname;
    private String consultant_address;
    private String consultant_email;
    private Long consultant_phonenumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="consultant")
    private Set<Portfolio> Portfolios;
}
