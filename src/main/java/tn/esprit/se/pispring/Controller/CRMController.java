package tn.esprit.se.pispring.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.se.pispring.Service.ConsultantService;
import tn.esprit.se.pispring.entities.Consultant;

import java.util.List;

@RestController
@RequestMapping("/CRM")
@Slf4j

public class CRMController {
    @Autowired
    private ConsultantService consultantService;

    @GetMapping("/retrieve-all-consultant")
    public List<Consultant> getconsultants() {
        List<Consultant> listConsultants = consultantService.retrieveAllConsultants();
        return listConsultants;
    }

    @GetMapping("/retrieve-consultant/{consultant-id}")
    public  Consultant retrieveconsultant(@PathVariable("consultant-id") Long consultantId) {
        return consultantService.retrieveConsultant(consultantId);
    }

    @PostMapping("/add-consultant")
    public  Consultant addconsultant(@RequestBody  Consultant c) {
        return consultantService.addConsultant(c) ;
    }

    @DeleteMapping("/remove-consultant/{consultant-id}")
    public void removeconsultant(@PathVariable("consultant-id") Long consultantId) {
        consultantService.removeConsultant(consultantId);
    }

    @PutMapping("/update-consultant")
    public Consultant updateconsultant(@RequestBody Consultant e) {
        Consultant consultant = consultantService.updateConsultant(e);
        return consultant;
    }
}
