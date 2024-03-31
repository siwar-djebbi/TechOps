package tn.esprit.se.pispring.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.Repository.ContributionRepository;
import tn.esprit.se.pispring.entities.Contribution;
import tn.esprit.se.pispring.entities.Prime;
import tn.esprit.se.pispring.entities.User;

import java.util.List;
@Service
@AllArgsConstructor
@Slf4j
public class ContributionImp implements ContributionService{
    ContributionRepository contributionRepository;
    @Override
    public List<Contribution> retrieveAllContributions() {
        return null;
    }

    @Override
    public Contribution addContribution(Contribution contribution) {
        return null;
    }

    @Override
    public Contribution updateContribution(Prime prime, Long idContribution) {
        return null;
    }

    @Override
    public Contribution retrieveContribution(Long idContribution) {
        return null;
    }

    @Override
    public void removeContribution(Long idContribution) {

    }

    public Float getSumAmountForUserMonthYear(Long userId, String month, Integer year) {
        return contributionRepository.getSumAmountForUserMonthYear(userId, month, year);
    }

    @Override
    public List<Contribution> getListContributionByUserAndMonth(User user, String contribution_month, Integer contribution_year) {
        return contributionRepository.findByUserAndContributionMonthAndContributionYear(user,contribution_month,contribution_year);
    }
}
