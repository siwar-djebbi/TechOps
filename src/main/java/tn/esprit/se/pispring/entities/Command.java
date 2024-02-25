package tn.esprit.se.pispring.entities;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Id;
import java.util.Date;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Command {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long commandId;
    @Temporal(TemporalType.DATE)
    private Date dateCommand;
    @Enumerated(EnumType.STRING)
    private CommandStatus commandStatus;

    @Enumerated(EnumType.STRING)
    private CommandPayment commandPayment;
    @ManyToOne
    User user;
    @OneToOne(mappedBy="command")
    private Cart cart;

}