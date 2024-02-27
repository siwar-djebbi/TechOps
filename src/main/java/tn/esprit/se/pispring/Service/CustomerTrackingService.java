package tn.esprit.se.pispring.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.Repository.CustomerTrackingRepository;
import tn.esprit.se.pispring.entities.CustomerTracking;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerTrackingService implements CustomerTrackingInterface{
 CustomerTrackingRepository customerTrackingRepository ;
    @Override
    public CustomerTracking addCustomerTracking(CustomerTracking ct) {
        return customerTrackingRepository.save(ct);
    }

    @Override
    public List<CustomerTracking> retrieveAllCustomerTrackings() {
        return null;
    }

    @Override
    public CustomerTracking updateCustomerTracking(CustomerTracking ct) {
        return customerTrackingRepository.save(ct);
    }

    @Override
    public CustomerTracking retrieveCustomerTracking(Long idCustomerTracking) {
        return null;
    }

    @Override
    public void removeCustomerTracking(Long idCustomerTracking) {

    }
}
