package tn.esprit.se.pispring.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.Repository.ConsultantRepository;
import tn.esprit.se.pispring.Repository.MeetRepository;
import tn.esprit.se.pispring.Repository.UserRepository;
import tn.esprit.se.pispring.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MeetService implements MeetInterface {
    @Autowired
    MeetRepository meetRepository;
    @Autowired
   ConsultantRepository consultantRepository ;
    @Autowired
    UserRepository userRepository ;
    @Override
    public List<Meeting> retrieveAllMeetings() {
        return meetRepository.findAll();
    }

    @Override
    public Meeting addMeeting(Meeting meeting) {
        return meetRepository.save(meeting);
    }

    @Override
    public Meeting updateMeeting(Meeting meeting) {
        return meetRepository.save(meeting);
    }

    @Override
    public Meeting getMeetingById(Long meetId) {
        return meetRepository.findById(meetId)
                .orElseThrow(() -> new EntityNotFoundException("meeting not found for this id :: " + meetId));
    }

    @Override
    public void deleteMeeting(Long meetId) {
        boolean exists = meetRepository.existsById(meetId);
        if (!exists) {
            throw new EntityNotFoundException("meeting not found for this id :: " + meetId);
        }
        meetRepository.deleteById(meetId);
    }

    @Override
    public void validerMeeting(Long meetId) {
        Meeting meeting = meetRepository.findById(meetId)
                .orElseThrow(() -> new EntityNotFoundException("Meeting not found for id: " + meetId));
        meeting.setMeetStatus(MeetStatus.APPROVED);
        Consultant consultant = meeting.getConsultant();
        if (consultant != null) {
            Long nb = consultant.getNbrFirstMeet();
            if (nb==null) {
              nb = 0L ;
            }
            if (nb != null && meeting.getFirst() != null && meeting.getFirst().equals(FirstMeet.oui)) {
                nb++;
                consultant.setNbrFirstMeet(nb);
            }
        }
        meetRepository.save(meeting);
    }

    public void affecter(Long meetId) {
        Meeting meeting = meetRepository.findById(meetId)
                .orElseThrow(() -> new EntityNotFoundException("Meeting not found for id: " + meetId));
        Consultant consultant = meeting.getConsultant();
        if (consultant != null) {
            Long nb = consultant.getNbrAffectation();
            if (nb==null) {
                nb = 0L ;
            }
            if (nb != null && meeting.getFirst() != null && meeting.getFirst().equals(FirstMeet.oui)) {
                nb++;
                consultant.setNbrAffectation(nb);
                meeting.setFirst(FirstMeet.non);
                meeting.setMeetStatus(MeetStatus.SUCCEDED);
                Portfolio p= consultant.getPortfolio() ;
                Long id = meeting.getUserId() ;
                if (id==null) {
                    id = 0L ;
                }
                else {
                    User user = userRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("User with id not found"));
                    user.setPortfolio(p);
                }

            }
        }
        meetRepository.save(meeting);
    }


    @Override
    public Meeting planifierMeeting(Meeting m, Long consultantId, Long userId) {
        Consultant consultant = consultantRepository.findById(consultantId)
                .orElseThrow(() -> new IllegalArgumentException("Consultant not found with id: " + consultantId));

        m.setConsultant(consultant);
        m.setUserId(userId);
        m.setMeetStatus(MeetStatus.PENDING);
        return meetRepository.save(m);
    }
    @Scheduled(cron = "0 0 0 * * *") // Déclenche la méthode tous les jours à minuit
    @Transactional // Assure que la transaction est active
    public void updateMeetingStatus() {
        List<Meeting> meetings = meetRepository.findMeetingsBeforeCurrentDate();
        for (Meeting meeting : meetings) {
            Consultant consultant = meeting.getConsultant();
            if (consultant != null ) {
                Long nb = consultant.getNbrPassedMeet();
                Long nbm = consultant.getNbrMeet();

                if (nb == null) {
                    nb = 0L;
                }
                if (nbm == null) {
                    nbm = 0L;
                }
                if (nb != null && nbm != null && meeting.getMeetStatus()!= null  && meeting.getMeetStatus().equals(MeetStatus.APPROVED)) {
                    nb++;
                    nbm++ ;
                    consultant.setNbrPassedMeet(nb);
                    consultant.setNbrMeet(nbm);
                    meeting.setMeetStatus(MeetStatus.PASSED);
                    meeting.getConsultant().getPortfolio().getMeeting_dates().add(meeting.getMeettdate()) ;

                }

            }
            meetRepository.save(meeting);
        }
    }
    @Override
    public void annulerMeet(Long meetId) {
        Meeting meeting = meetRepository.findById(meetId)
                .orElseThrow(() -> new EntityNotFoundException("Meeting not found for id: " + meetId));
        meeting.setMeetStatus(MeetStatus.CANCELED);
        meeting.getConsultant().getNbrCanceledMeet() ;
        meetRepository.save(meeting);

        Consultant consultant = meeting.getConsultant();
        if (consultant != null ) {
            Long nbm = consultant.getNbrMeet();
            if (nbm == null) {
                nbm = 0L;
            }
            else {
                nbm++;
                consultant.setNbrMeet(nbm);
            }
        }

    }

   /* @Scheduled(cron = "0 0 0 * * *")
    public void cleanUpCanceledMeetings() {
        // Récupérer les meetings avec le statut "CANCELED"
        List<Meeting> canceledMeetings = meetRepository.findByMeetStatus(MeetStatus.CANCELED);

        // Supprimer les meetings annulés
        meetRepository.deleteAll(canceledMeetings);
    }*/


    public Map<String, Double> calculateMeetingStatistics() {
        List<Meeting> meetings = meetRepository.findAll();
        int totalMeetings = meetings.size();
        int cancelledMeetingsCount = 0;
        int approuvededMeetingsCount = 0;
        int passedMeetingsCount = 0;

        for (Meeting meeting : meetings) {

            if (meeting.getMeetStatus() == MeetStatus.CANCELED) {
                cancelledMeetingsCount++;
            }
            if (meeting.getMeetStatus() == MeetStatus.APPROVED) {
                approuvededMeetingsCount++;
            }
            if (meeting.getMeetStatus() == MeetStatus.PASSED) {
                passedMeetingsCount++;
            }

        }

        double cancelledMeetingsFrequency = (double) cancelledMeetingsCount / totalMeetings * 100;

        Map<String, Double> meetingStatistics = new HashMap<>();
        meetingStatistics.put("totalMeetings", (double) totalMeetings);

        meetingStatistics.put("cancelledMeetingsCount", (double) cancelledMeetingsCount);
        meetingStatistics.put("cancelledMeetingsFrequency", cancelledMeetingsFrequency);


        return meetingStatistics;
    }
    public Map<Consultant, Map<String, Object>> calculateConsultantStats() {
        Map<Consultant, Map<String, Object>> statsMap = new HashMap<>();
        List<Consultant> consultants = consultantRepository.findAll();

        for (Consultant consultant : consultants) {
            long totalDuration = 0;
            long totalMeetings = 0;

            for (Meeting meeting : consultant.getMeetings()) {
                // Increment total duration with meeting duration
                totalDuration += meeting.getDureeReunion();
                totalMeetings++;
            }

            // Create a new map to store stats for the current consultant
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalMeetings", totalMeetings);
            stats.put("totalDuration", totalDuration);

            // Add stats to the stats map with the consultant as key
            statsMap.put(consultant, stats);
        }

        return statsMap;
    }

    public Map<LocalDate, Long> countMeetingsByDateAndConsultantId(Long consultantId, Date dateBegin, Date dateEnd) {
        Map<LocalDate, Long> meetingCountsByDate = new HashMap<>();

        // Convertir les dates en LocalDate
        LocalDate localDateBegin = dateBegin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateEnd = dateEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Parcourir chaque date entre dateBegin et dateEnd
        for (LocalDate date = localDateBegin; !date.isAfter(localDateEnd); date = date.plusDays(1)) {
            meetingCountsByDate.put(date, 0L);
        }

        // Récupérer le consultant par son ID
        Consultant consultant = consultantRepository.findById(consultantId).orElse(null);

        if (consultant != null) {
            // Récupérer les réunions du consultant entre dateBegin et dateEnd
            List<Meeting> meetings = meetRepository.findByConsultantAndMeettdateBetween(consultant, dateBegin, dateEnd);

            for (Meeting meeting : meetings) {
                java.util.Date utilDate = new java.util.Date(meeting.getMeettdate().getTime());
                LocalDate meetingDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Long count = meetingCountsByDate.getOrDefault(meetingDate, 0L);
                meetingCountsByDate.put(meetingDate, count + 1);
            }

        }

        return meetingCountsByDate;

    }
   public List<Meeting> getMeetingsByConsultantId(Long consultantId) {
    List<Meeting> meets = meetRepository.findAll() ;
       List<Meeting> meetConsultants = new ArrayList<>() ;

       for ( Meeting meeting : meets ){
        if (meeting.getConsultant().getConsultant_id().equals(consultantId)) {
            meetConsultants.add(meeting) ;
        }
    }

            return meetConsultants ;
   }
        // Recherchez la réunion en attente pour l'utilisateur donné
        public List<Meeting> findFirstPendingOrApprovedMeetingByUserId(Long userId) {
         List<Meeting> meetings = meetRepository.findAll() ;
         List<Meeting> userMeeting =  new ArrayList<>() ;
         List<Meeting>  meets= new ArrayList<>() ;
            for(Meeting m :meetings){
                if(m.getUserId().equals(userId) ) {
                    userMeeting.add(m);
                }
            }
            for (Meeting meeting : userMeeting){
                if(meeting.getMeetStatus().equals(MeetStatus.PENDING)){
                    meets.add(meeting) ;
                } else if (meeting.getMeetStatus().equals(MeetStatus.APPROVED)) {
                    meets.add(meeting) ;
                }
            }
            return  meets ;
        }
         public List<Meeting> meetingsPourUser (Long userId){
             List<Meeting> meetings = meetRepository.findAll() ;
             List<Meeting> userMeeting =  new ArrayList<>() ;
             for(Meeting m :meetings){
                 if(m.getUserId().equals(userId) ) {
                     userMeeting.add(m);
                 }
             }
             return  userMeeting ;
         }
    }


