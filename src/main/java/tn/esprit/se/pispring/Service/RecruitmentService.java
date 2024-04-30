package tn.esprit.se.pispring.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.Repository.RecruitmentRepository;
import tn.esprit.se.pispring.Repository.UserRepository;
import tn.esprit.se.pispring.entities.Recruitment;
import tn.esprit.se.pispring.entities.RecruitmentStatus;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecruitmentService implements IRecruitmentService {

    private final UserRepository userRepository;
    private final RecruitmentRepository recruitmentRepository;
    @Override
    public Recruitment addOrUpdateRecruitment(Recruitment R) {
        return recruitmentRepository.save(R);
    }

    @Override
    public void removeRecruitment(Long offerId) {
        recruitmentRepository.deleteById(offerId);
    }

    @Override
    public Recruitment retrieveRecruitment(Long offerId) {
        return recruitmentRepository.findById(offerId).orElse(null);
    }

    @Override
    public List<Recruitment> retrieveAllRecruitments() {
        return recruitmentRepository.findAll();
    }

    @Override
    public List<String> getPostTitles() {
        List<Recruitment> recruitments = recruitmentRepository.findAll();
        return recruitments.stream()
                .map(Recruitment::getPostTitle) // Assuming 'getPostTitle()' returns the post title
                .collect(Collectors.toList());
    }
    @Override
    public Optional<Recruitment> getPostTitleDetails(String postTitle) {
        return recruitmentRepository.findByPostTitle(postTitle);
    }
    @Override
    public double calculateAverageSalaryRange() {
        List<Recruitment> recruitments = retrieveAllRecruitments();
        double totalSalaryRange = 0;
        for (Recruitment recruitment : recruitments) {
            totalSalaryRange += (recruitment.getSalaryRangeMax() + recruitment.getSalaryRangeMin()) / 2;
        }
        return totalSalaryRange / recruitments.size();
    }
    @Override
    public Map<String, Integer> getRecruitmentsPerHiringManager() {
        List<Recruitment> recruitments = retrieveAllRecruitments();
        Map<String, Integer> recruitmentsByManager = new HashMap<>();
        for (Recruitment recruitment : recruitments) {
            String hiringManager = recruitment.getHiringManager();
            recruitmentsByManager.put(hiringManager, recruitmentsByManager.getOrDefault(hiringManager, 0) + 1);
        }
        return recruitmentsByManager;
    }
    @Override
    public Map<String, Integer> getOpenPositionsByLocation() {
        List<Recruitment> recruitments = retrieveAllRecruitments();
        Map<String, Integer> positionsByLocation = new HashMap<>();
        for (Recruitment recruitment : recruitments) {
            String jobLocation = recruitment.getJobLocation();
            positionsByLocation.put(jobLocation, positionsByLocation.getOrDefault(jobLocation, 0) + recruitment.getNumberOfOpenings());
        }
        return positionsByLocation;
    }
    @Override
    public Map<String, Map<RecruitmentStatus, Integer>> analyzeRecruitmentTrend(int startYear, int endYear) {
        Map<String, Map<RecruitmentStatus, Integer>> trendData = new HashMap<>();

        List<RecruitmentStatus> statuses = Arrays.asList(RecruitmentStatus.OPEN, RecruitmentStatus.CLOSED);

        for (int year = startYear; year <= endYear; year++) {
            Map<RecruitmentStatus, Integer> statusCounts = new HashMap<>();
            List<Object[]> results = recruitmentRepository.countRecruitmentStatusByYear(year, statuses);
            for (Object[] result : results) {
                RecruitmentStatus status = (RecruitmentStatus) result[0];
                Long count = (Long) result[1];
                statusCounts.put(status, count.intValue());
            }
            trendData.put(String.valueOf(year), statusCounts);
        }

        return trendData;
    }
}
