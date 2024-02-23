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
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long productId;
    private Integer quantity;
    private String reference;
    private String title;
    private String image;
    private String description;
    private Long stock;
    private Float price;
    private Float TVA;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @ManyToMany(mappedBy="products", cascade = CascadeType.ALL)
    private Set<Cart> carts;

    @Enumerated(EnumType.STRING)
    private ProductType productType ;
}

