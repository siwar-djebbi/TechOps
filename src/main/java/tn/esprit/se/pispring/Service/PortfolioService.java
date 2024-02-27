package tn.esprit.se.pispring.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.Repository.PortfilioRepository;
import tn.esprit.se.pispring.entities.Portfolio;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PortfolioService implements PortfolioInterface{
    PortfilioRepository portfilioRepository;
    @Override
    public Portfolio addPortfolio(Portfolio p) {
        return portfilioRepository.save(p);
    }

    @Override
    public List<Portfolio> retrieveAllPortfolios() {
        return portfilioRepository.findAll();
    }

    @Override
    public Portfolio updatePortfolio(Portfolio p) {
        return portfilioRepository.save(p);
    }

    @Override
    public Portfolio retrievePortfolio(Long idPortfolio) {
        return portfilioRepository.findById(idPortfolio).get();
    }

    @Override
    public void removePortfolio(Long idPortfolio) {
    portfilioRepository.deleteById(idPortfolio);
    }
}
