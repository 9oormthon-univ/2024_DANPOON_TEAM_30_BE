package soon.ready_action.domain.knowledge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.ready_action.domain.knowledge.dto.KnowledgeResponse;
import soon.ready_action.domain.knowledge.dto.KnowledgeResponse.DetailResponse;
import soon.ready_action.domain.knowledge.entity.Knowledge;
import soon.ready_action.domain.knowledge.repository.KnowledgeRepository;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KnowledgeService {

    private final KnowledgeRepository knowledgeRepository;

    // 전체 조회
    @Transactional(readOnly = true)
    public KnowledgeResponse getKnowledgeByCategory(Long categoryId, int size, Long lastKnowledgeId) {
        List<Knowledge> knowledgeList;

        // 페이지 요청 생성
        Pageable pageable = PageRequest.of(0, size);
        if (lastKnowledgeId == null) {
            // 첫 페이지인 경우
            knowledgeList = knowledgeRepository.findFirstKnowledge(categoryId, pageable);
        } else {
            // 이후 페이지인 경우
            knowledgeList = knowledgeRepository.findKnowledgeAfterId(categoryId, lastKnowledgeId, pageable);
        }

        // 결과 매핑
        List<KnowledgeResponse.KnowledgeContent> contents = knowledgeList.stream()
                .map(knowledge -> new KnowledgeResponse.KnowledgeContent(
                        knowledge.getId(),
                        knowledge.getTitle(),
                        knowledge.getContent()
                ))
                .collect(Collectors.toList());

        // 카테고리 타이틀 추출
        String categoryTitle = knowledgeList.isEmpty() ? "" : knowledgeList.get(0).getCategory().getTitle();

        // 다음 페이지 여부 확인
        boolean hasNextPage = knowledgeList.size() == size;

        return new KnowledgeResponse(categoryTitle, contents, knowledgeList.size(), hasNextPage);
    }


    // 상세 조회
    @Transactional(readOnly = true)
    public DetailResponse getKnowledgeById(Long knowledgeId) {
        Knowledge knowledge = knowledgeRepository.findById(knowledgeId)
                .orElseThrow(() -> new IllegalArgumentException("Knowledge not found with id: " + knowledgeId));

        return new DetailResponse(
                knowledge.getId(),
                knowledge.getTitle(),
                knowledge.getContent(),
                knowledge.getCategory().getTitle()
        );
    }
}
