package tn.esprit.se.pispring.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.Repository.CandidateRepository;
import tn.esprit.se.pispring.Repository.RecruitmentRepository;
import tn.esprit.se.pispring.entities.Candidate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateService implements ICandidateService {
    private final RecruitmentRepository recruitmentRepository;

    private final CandidateRepository candidateRepository;
    @Override
    public Candidate addOrUpdateCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }
    @Override
    public void removeCandidate(Long id) {
        candidateRepository.deleteById(id);
    }
    @Override
    public Candidate retrieveCandidate(Long id) {
        return candidateRepository.findById(id).orElse(null);
    }
    @Override
    public List<Candidate> retrieveAllCandidates() {
        return candidateRepository.findAll();
    }


}

