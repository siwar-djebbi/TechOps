package tn.esprit.se.pispring.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Leave {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long leaveId;
    private String leaveType ;
    private Date startDate ;
    private Date endDate;
    private String leaveStatus;

    @Enumerated(EnumType.STRING)
    private LeaveStatus Status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="leave")
    private Set<User> Users;


}
