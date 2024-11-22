package soon.ready_action.domain.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.main.dto.response.MainResponse;
import soon.ready_action.domain.membercategory.service.MemberCategoryService;
import soon.ready_action.domain.program.service.ProgramService;
import soon.ready_action.domain.knowledge.service.KnowledgeService;
import soon.ready_action.domain.program.dto.response.ProgramResponse.ProgramContent;
import soon.ready_action.domain.knowledge.dto.KnowledgeResponse.KnowledgeContent;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainService {

    private final MemberCategoryService memberCategoryService;
    private final ProgramService programService;
    private final KnowledgeService knowledgeService;

    // Main API: 회원이 속한 카테고리와 관련된 최신 프로그램 3개, 자립 지식 최신 3개 반환
    public MainResponse getMainPage(Long memberId) {
        // 회원의 카테고리 가져오기
        List<Category> categories = memberCategoryService.getCategoriesByMemberId(memberId);

        // 카테고리 ID 리스트
        List<Long> categoryIds = categories.stream()
                .map(Category::getId)
                .collect(Collectors.toList());

        // 해당 카테고리의 최신 프로그램 3개 조회
        List<ProgramContent> programContents = programService.getLatestProgramsByCategories(categoryIds);

        // 최신 3개의 자립 지식 조회
        List<KnowledgeContent> knowledgeContents = knowledgeService.getLatestKnowledge();

        // MainResponse 반환
        return new MainResponse(programContents, knowledgeContents);
    }
}
