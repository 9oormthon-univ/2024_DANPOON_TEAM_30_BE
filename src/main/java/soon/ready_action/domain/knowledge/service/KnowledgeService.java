package soon.ready_action.domain.knowledge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.category.repository.CategoryRepository;  // CategoryRepository import 추가
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
    private final CategoryRepository categoryRepository;

    // 전체 조회
    @Transactional(readOnly = true)
    public KnowledgeResponse getKnowledgeByCategory(String categoryTitle, int page) {
        // 기본 페이지 크기 설정 (5개)
        int size = 5;

        // 페이지 번호를 0-based로 변환 (1부터 시작하므로 -1 처리)
        Pageable pageable = PageRequest.of(page - 1, size);

        // categoryTitle로 카테고리 객체를 찾음
        Category category = categoryRepository.findByTitle(categoryTitle);

        // 해당 카테고리의 지식 조회
        List<Knowledge> knowledgeList = knowledgeRepository.findKnowledgeByCategoryTitle(categoryTitle, pageable);

        // 결과 매핑
        List<KnowledgeResponse.KnowledgeContent> contents = knowledgeList.stream()
                .map(knowledge -> new KnowledgeResponse.KnowledgeContent(
                        knowledge.getId(),
                        knowledge.getTitle(),
                        knowledge.getContent()
                ))
                .collect(Collectors.toList());

        return new KnowledgeResponse(categoryTitle, contents);
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

    // 최신 3개의 자립 지식 조회
    public List<KnowledgeResponse.KnowledgeContent> getLatestKnowledge() {
        List<Knowledge> knowledgeList = knowledgeRepository.findTop3ByOrderByIdDesc();

        return knowledgeList.stream()
                .map(knowledge -> new KnowledgeResponse.KnowledgeContent(
                        knowledge.getId(),
                        knowledge.getTitle(),
                        knowledge.getContent()
                ))
                .collect(Collectors.toList());
    }
}
