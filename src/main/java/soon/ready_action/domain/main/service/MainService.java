package soon.ready_action.domain.main.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.diagnosis.service.DiagnosisScoreService;
import soon.ready_action.domain.knowledge.dto.KnowledgeResponse.KnowledgeContent;
import soon.ready_action.domain.knowledge.service.KnowledgeService;
import soon.ready_action.domain.main.dto.response.MainResponse;
import soon.ready_action.domain.membercategory.service.MemberCategoryService;
import soon.ready_action.domain.program.dto.response.ProgramResponse;
import soon.ready_action.domain.program.service.ProgramService;

@Service
@RequiredArgsConstructor
public class MainService {

    private final MemberCategoryService memberCategoryService;
    private final ProgramService programService;
    private final KnowledgeService knowledgeService;
    private final DiagnosisScoreService diagnosisScoreService;

    // Main API: 회원이 속한 카테고리와 관련된 최신 프로그램 3개, 자립 지식 최신 3개 반환
    public MainResponse getMainPage(Long memberId) {
        // 회원의 카테고리 가져오기
        List<Category> categories = memberCategoryService.getCategoriesByMemberId(memberId);

        // 카테고리 ID 리스트
        List<Long> categoryIds = categories.stream()
            .map(Category::getId)
            .collect(Collectors.toList());

        // Pageable 객체 생성 (최대 3개 프로그램)
        Pageable pageable = PageRequest.of(0, 3); // 0번째 페이지, 3개 항목

        // 해당 카테고리의 최신 프로그램 3개 조회 (DetailResponse 사용)
        List<ProgramResponse.DetailResponse> programDetails = programService.getLatestProgramsByCategories(
            categoryIds, pageable, memberId);

        // 최신 3개의 자립 지식 조회
        List<KnowledgeContent> knowledgeContents = knowledgeService.getLatestKnowledge();

        // 자가진단 점수 확인
        int diagnosisScore = diagnosisScoreService.getTotalScoreByMemberId(memberId);
        String selfDiagnosis = getDiagnosisLevel(diagnosisScore);

        // MainResponse 반환
        return new MainResponse(programDetails, knowledgeContents, selfDiagnosis);
    }

    // 자가진단 점수를 범위에 따라 캐릭터 타입 반환
    private String getDiagnosisLevel(int diagnosisScore) {
        if (diagnosisScore >= 0 && diagnosisScore <= 9) {
            return "ONE";
        } else if (diagnosisScore >= 10 && diagnosisScore <= 19) {
            return "TWO";
        } else if (diagnosisScore >= 20 && diagnosisScore <= 29) {
            return "THREE";
        } else if (diagnosisScore >= 30 && diagnosisScore <= 39) {
            return "FOUR";
        } else if (diagnosisScore >= 40 && diagnosisScore <= 50) {
            return "FIVE";
        } else {
            return "자가진단 점수 범위 초과 에러";
        }
    }
}
