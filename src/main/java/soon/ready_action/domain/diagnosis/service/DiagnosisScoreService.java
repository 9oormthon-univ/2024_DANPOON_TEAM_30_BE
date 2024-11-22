package soon.ready_action.domain.diagnosis.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.category.repository.CategoryRepository;
import soon.ready_action.domain.diagnosis.dto.CalculateDiagnosisResult;
import soon.ready_action.domain.diagnosis.entity.DiagnosisCategoryScore;
import soon.ready_action.domain.diagnosis.entity.DiagnosisResult;
import soon.ready_action.domain.diagnosis.repository.DiagnosisCategoryScoreRepository;
import soon.ready_action.domain.diagnosis.repository.DiagnosisResultRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiagnosisScoreService {

    private final DiagnosisCategoryScoreRepository scoreRepository;
    private final DiagnosisResultRepository resultRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void calculateAndSaveDiagnosisScores(Long loginMemberId) {
        List<Category> categories = categoryRepository.findAll();
        Map<String, Category> categoryMap = categories.stream()
            .collect(Collectors.toMap(Category::getTitle, category -> category));

        List<CalculateDiagnosisResult> calculateDiagnosisResults = calculateScore(
            categories, loginMemberId
        );
        List<DiagnosisCategoryScore> existingScores = scoreRepository.findByMemberId(loginMemberId);
        Map<Long, DiagnosisCategoryScore> existingScoresMap = toScoreMap(existingScores);

        List<DiagnosisCategoryScore> scoresToSave = calculateDiagnosisResults.stream()
            .filter(result -> result.score() >= 8)
            .map(result -> processScore(result, categoryMap, existingScoresMap, loginMemberId))
            .collect(Collectors.toList());

        scoreRepository.saveAll(scoresToSave);
    }

    @Transactional(readOnly = true)
    public List<CalculateDiagnosisResult> calculateScore(List<Category> categories, Long memberId) {
        return categories.stream().map(category -> {
            List<DiagnosisResult> resultsByCategory = resultRepository
                .findDiagnosisResultsByMemberAndCategoryAndAnswerType(
                    memberId, category.getTitle()
                );

            return CalculateDiagnosisResult.builder()
                .score(resultsByCategory.size())
                .categoryTitle(category.getTitle())
                .build();

        }).toList();
    }

    private Map<Long, DiagnosisCategoryScore> toScoreMap(
        List<DiagnosisCategoryScore> existingScores
    ) {
        return existingScores.stream()
            .collect(Collectors.toMap(DiagnosisCategoryScore::getCategoryId, score -> score,
                (existing, replacement) -> existing)
            );
    }

    private DiagnosisCategoryScore processScore(
        CalculateDiagnosisResult result,
        Map<String, Category> categoryMap,
        Map<Long, DiagnosisCategoryScore> existingScoresMap,
        Long loginMemberId
    ) {
        Category category = categoryMap.get(result.categoryTitle());
        DiagnosisCategoryScore existingScore = existingScoresMap.get(category.getId());

        if (existingScore != null) {
            existingScore.updateScore(result.score());
            return existingScore;
        } else {
            return DiagnosisCategoryScore.builder()
                .score(result.score())
                .categoryId(category.getId())
                .memberId(loginMemberId)
                .build();
        }
    }
}
