package tn.esprit.se.pispring.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCandidate;

    private String firstNameCand;
    private String lastNameCand;
    private String emailCand;
    private String skillsCand;
    private String experienceCand;
    private String education;
    @Lob
    private byte[] cv; // Byte array to store the file content

    private String cvFileName; // Name of the CV file

}
