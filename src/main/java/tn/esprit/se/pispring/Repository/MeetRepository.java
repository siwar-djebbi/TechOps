package tn.esprit.se.pispring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.se.pispring.entities.Consultant;
import tn.esprit.se.pispring.entities.MeetStatus;
import tn.esprit.se.pispring.entities.Meeting;

import java.util.Date;
import java.util.List;

public interface MeetRepository extends JpaRepository<Meeting,Long> {
    List<Meeting> findByMeetStatus(MeetStatus meetStatus);
    List<Meeting> findByConsultantAndMeettdateBetween(Consultant consultant, Date dateBegin, Date dateEnd);


    @Query("SELECT m FROM Meeting m WHERE m.meettdate< CURRENT_DATE()")
    List<Meeting> findMeetingsBeforeCurrentDate();}
