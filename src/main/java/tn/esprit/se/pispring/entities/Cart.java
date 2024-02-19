package tn.esprit.se.pispring.entities;

import jakarta.persistence.*;
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
public class Cart {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long cart_id;
    @Temporal(TemporalType.DATE)
    private Date datelastitem;
    private Float cart_amount;
    private Integer cart_items_number;
    @Enumerated(EnumType.STRING)
    private CartPayment cartPayment;
    @ManyToOne
    User user;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Product> products;
    @OneToOne
    private Invoice invoice;
}
