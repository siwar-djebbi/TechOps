package tn.esprit.se.pispring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long cartId;
    @Temporal(TemporalType.DATE)
    private Date datelastitem;
    private Float totalAmount;
    private Float subTotal;
    private Integer numberOfItems;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Product> products;
    @OneToOne
    private Command command;

}
