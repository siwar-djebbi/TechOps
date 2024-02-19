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
public class Supplier {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long supplier_id;
    private String supplier_firstname;
    private String supplier_lastname;
    private Long supplier_phonenumber;
    private String supplier_email;

    @ManyToMany(mappedBy="suppliers", cascade = CascadeType.ALL)
    private Set<Product> products;


}
