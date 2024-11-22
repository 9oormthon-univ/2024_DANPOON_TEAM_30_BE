package soon.ready_action.domain.badge.service;

import static soon.ready_action.domain.diagnosis.repository.DiagnosisCategoryScoreRepository.STANDARD_SCORE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.ready_action.domain.badge.entity.Badge;
import soon.ready_action.domain.badge.entity.BadgeType;
import soon.ready_action.domain.badge.repository.BadgeRepository;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.category.repository.CategoryRepository;
import soon.ready_action.domain.diagnosis.dto.CalculateDiagnosisResult;
import soon.ready_action.domain.diagnosis.service.DiagnosisScoreService;
import soon.ready_action.domain.member.entity.Member;

@RequiredArgsConstructor
@Service
public class BadgeService {

    private final DiagnosisScoreService scoreService;
    private final CategoryRepository categoryRepository;
    private final BadgeRepository badgeRepository;

    @Transactional
    public void awardBadgesForStandardScores(Member member) {
        List<Category> categories = categoryRepository.findAll();
        List<CalculateDiagnosisResult> results = scoreService.calculateScore(
            categories, member.getId()
        );

        List<BadgeType> existingBadgeTypes = badgeRepository.findBadgeTypesByMemberId(member);

        results.stream()
            .filter(this::isStandardScore)
            .filter(result -> !existingBadgeTypes.contains(BadgeType.of(result.categoryTitle())))
            .forEach(result -> createAndSaveBadge(member, result));
    }

    private boolean isStandardScore(CalculateDiagnosisResult result) {
        return result.score() >= STANDARD_SCORE;
    }

    private void createAndSaveBadge(Member member, CalculateDiagnosisResult result) {
        Badge badge = Badge.builder()
            .type(BadgeType.of(result.categoryTitle()))
            .member(member)
            .build();

        badgeRepository.save(badge);
    }
}
