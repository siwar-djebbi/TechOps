package tn.esprit.se.pispring.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String nom ;
    private String email;
    private String password;
    private int telephone;
    private String rating;

    @Enumerated(EnumType.STRING)
    private TypeRole role;

    private Boolean connected = false;






}

