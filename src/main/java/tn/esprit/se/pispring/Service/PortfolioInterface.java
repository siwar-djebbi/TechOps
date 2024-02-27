package tn.esprit.se.pispring.Service;

import org.springframework.stereotype.Repository;
import tn.esprit.se.pispring.entities.Portfolio;

import java.util.List;

@Repository
public interface PortfolioInterface {
    Portfolio addPortfolio(Portfolio p) ;

    List<Portfolio> retrieveAllPortfolios();


    Portfolio updatePortfolio(Portfolio p);

    Portfolio retrievePortfolio(Long idPortfolio);

    void removePortfolio(Long idPortfolio);
}
