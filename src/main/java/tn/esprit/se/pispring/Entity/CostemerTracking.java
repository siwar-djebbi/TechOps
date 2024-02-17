package tn.esprit.se.pispring.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CostemerTracking {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idHistory;
    private String Description  ;
    private Date MeetDate ;

    @OneToOne(mappedBy="costemertracking")
    private PorteFeuille portefeuille;

}
