package tn.esprit.se.pispring.entities;

import javax.persistence.*;
import lombok.*;

import java.util.Date;
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
    private String client_name;
    @Temporal(TemporalType.DATE)
    private Date creation_date;
    private String potfolio_manager;
    private String potfolio_description;

    @OneToOne(mappedBy="portfolio")
    private Consultant consultant;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="portfolio")
    private Set<User> Users;



}
