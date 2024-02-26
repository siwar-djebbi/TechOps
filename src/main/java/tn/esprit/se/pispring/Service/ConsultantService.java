package tn.esprit.se.pispring.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.Repository.ConsultantRepository;
import tn.esprit.se.pispring.entities.Consultant;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ConsultantService implements ConsultantInterface{
    ConsultantRepository consultantRepository ;
    @Override
    public Consultant addConsultant(Consultant c) {
        return consultantRepository.save(c);
    }
    @Override
    public Consultant updateConsultant(Consultant c) {
        return consultantRepository.save(c);
    }

    @Override
     public List<Consultant> retrieveAllConsultants() {
         return consultantRepository.findAll() ;
     }

    @Override
    public Consultant retrieveConsultant(Long idConsultant) {
        return consultantRepository.findById(idConsultant).get();
    }

    @Override
    public void removeConsultant(Long idConsultant) {
        consultantRepository.deleteById(idConsultant);
    }


}
