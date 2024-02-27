package tn.esprit.se.pispring.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.se.pispring.Service.PortfolioService;
import tn.esprit.se.pispring.entities.Portfolio;

import java.util.List;

@RestController
@RequestMapping("/PORTFOLIO")
@Slf4j

public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService ;

    @GetMapping("/retrieve-all-portfolio")
    public List<Portfolio> getPortfolios() {
        List<Portfolio> listPortfolios = portfolioService.retrieveAllPortfolios();
        return listPortfolios;
    }

    @GetMapping("/retrieve-Portfolio/{portfolio-id}")
    public  Portfolio retrievePortfolio(@PathVariable("portfolio-id") Long portfolioId) {
        return portfolioService.retrievePortfolio(portfolioId);
    }

    @PostMapping("/add-Portfolio")
    public  Portfolio addPortfolio(@RequestBody  Portfolio p) {
        return portfolioService.addPortfolio(p) ;
    }

    @DeleteMapping("/remove-portfolio/{portfolio-id}")
    public void removePortfolio(@PathVariable("portfolio-id") Long portfolioId) {
        portfolioService.removePortfolio(portfolioId);
    }

    @PutMapping("/update-portfolio")
    public Portfolio updatePortfolio(@RequestBody Portfolio p) {
        Portfolio portfolio = portfolioService.updatePortfolio(p);
        return portfolio;
    }
}
