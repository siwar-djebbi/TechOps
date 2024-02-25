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
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long product_id;
    private Integer product_quantity;
    private String product_reference;
    private String product_title;
    private String product_image;
    private String product_description;
    private Long stock;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @ManyToMany(mappedBy="products", cascade = CascadeType.ALL)
    private Set<Cart> carts;

    @Enumerated(EnumType.STRING)
    private ProductType productType ;
}

