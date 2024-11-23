package soon.ready_action.domain.knowledge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import soon.ready_action.domain.knowledge.entity.Knowledge;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KnowledgeRepository extends JpaRepository<Knowledge, Long> {

    @Query("SELECT k FROM Knowledge k WHERE k.category.title = :categoryTitle ORDER BY k.id DESC")
    List<Knowledge> findKnowledgeByCategoryTitle(@Param("categoryTitle") String categoryTitle, Pageable pageable);

    // 최신 3개의 자립 지식 조회
    List<Knowledge> findTop3ByOrderByIdDesc();
}
