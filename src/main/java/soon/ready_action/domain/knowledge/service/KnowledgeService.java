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

    // 무한 스크롤 조회
    @Transactional(readOnly = true)
    public KnowledgeResponse getKnowledgeByCategory(Long categoryId, int size, Long lastKnowledgeId) {
        List<Knowledge> knowledgeList;

        if (lastKnowledgeId == null) {
            // 첫 페이지인 경우
            Pageable pageable = PageRequest.of(0, size);
            knowledgeList = knowledgeRepository.findFirstKnowledge(categoryId, pageable);
        } else {
            // 이후 페이지인 경우
            Pageable pageable = PageRequest.of(0, size); // 페이지 번호는 0으로 고정
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

        Long nextCursor = knowledgeList.size() < size ? -1 : knowledgeList.get(knowledgeList.size() - 1).getId();

        return new KnowledgeResponse(contents, knowledgeList.size(), nextCursor);
    }


    // 상세 조회
    @Transactional(readOnly = true)
    public DetailResponse getKnowledgeById(Long knowledgeId) {
        Knowledge knowledge = knowledgeRepository.findById(knowledgeId)
                .orElseThrow(() -> new IllegalArgumentException("Knowledge not found with id: " + knowledgeId));

        return new DetailResponse(
                knowledge.getId(),
                knowledge.getTitle(),
                knowledge.getContent()
        );
    }
}