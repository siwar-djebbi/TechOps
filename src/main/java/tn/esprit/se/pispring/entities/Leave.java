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
public class Leave {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long leaveId;

    @Temporal(TemporalType.DATE)
    private Date leaveStartdate;

    @Temporal(TemporalType.DATE)
    private Date leaveEnddate;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    private LeaveStatus leaveStatus;

    private String reason;

    private boolean leaveApproved;

    private Date requestDate;

    private String leaveApproverName;

    private String comments;

    private Integer leaveDaysLeft; // Number of leave days left for the employee
    @ManyToOne
    User user;

}
