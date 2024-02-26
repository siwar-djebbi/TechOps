package tn.esprit.se.pispring.Service;

import org.springframework.stereotype.Repository;
import tn.esprit.se.pispring.entities.Consultant;

import java.util.List;

@Repository

public interface ConsultantInterface {
    Consultant addConsultant(Consultant c) ;

    List<Consultant> retrieveAllConsultants();


    Consultant updateConsultant(Consultant c);

    Consultant retrieveConsultant(Long idConsultant);

    void removeConsultant(Long idConsultant);
    }
