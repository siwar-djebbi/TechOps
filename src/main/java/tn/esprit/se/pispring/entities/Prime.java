package tn.esprit.se.pispring.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Prime {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long prime_id;
    private Float base_value;
    private String prime_designation;
    private Float value_amount;

    @ManyToOne
    User user;


}
