package tn.esprit.se.pispring.Service;

import tn.esprit.se.pispring.entities.Contribution;
import tn.esprit.se.pispring.entities.Prime;
import tn.esprit.se.pispring.entities.User;

import java.util.List;

public interface ContributionService {
    List<Contribution> retrieveAllContributions();
    Contribution addContribution(Contribution contribution);
    Contribution updateContribution(Prime prime, Long idContribution);
    Contribution retrieveContribution(Long idContribution);
    void removeContribution(Long idContribution);
    Float getSumAmountForUserMonthYear(Long userId, String month, Integer year);
    List<Contribution> getListContributionByUserAndMonth(User user, String contribution_month, Integer contribution_year);

}
