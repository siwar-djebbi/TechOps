package tn.esprit.se.pispring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long invoice_id;
    @Temporal(TemporalType.DATE)
    private Date emission_date;
    private Float invoice_amount;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;
    @OneToOne(mappedBy="invoice")
    private Cart cart;

}
