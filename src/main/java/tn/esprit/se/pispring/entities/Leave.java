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
    private Long leave_id;

    @Temporal(TemporalType.DATE)
    private Date leave_startdate;

    @Temporal(TemporalType.DATE)
    private Date leave_enddate;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;
    @Enumerated(EnumType.STRING)
    private LeaveStatus leaveStatus;
    @ManyToOne
    User user;

}
