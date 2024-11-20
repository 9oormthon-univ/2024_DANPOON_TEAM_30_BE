package soon.ready_action.domain.knowledge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import soon.ready_action.domain.knowledge.entity.Knowledge;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KnowledgeRepository extends JpaRepository<Knowledge, Long> {

    @Query("SELECT k FROM Knowledge k WHERE k.category.id = :categoryId AND k.id < :lastKnowledgeId ORDER BY k.id DESC")
    List<Knowledge> findKnowledgeAfterId(@Param("categoryId") Long categoryId, @Param("lastKnowledgeId") Long lastKnowledgeId, Pageable pageable);

    @Query("SELECT k FROM Knowledge k WHERE k.category.id = :categoryId ORDER BY k.id DESC")
    List<Knowledge> findFirstKnowledge(@Param("categoryId") Long categoryId, Pageable pageable);
}
