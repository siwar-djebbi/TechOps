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
public class Portfolio {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long potfolio_id;
    private String potfolio_name;
    private String potfolio_manager;
    private String potfolio_description;

    @OneToOne(mappedBy="portfolio")
    private Consultant consultant;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="portfolio")
    private Set<User> Users;



}
