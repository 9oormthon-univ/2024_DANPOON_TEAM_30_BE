package soon.ready_action.domain.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import soon.ready_action.domain.program.entity.Program;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {

    // 검색
    @Query("SELECT p FROM Program p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY p.id DESC")
    List<Program> searchProgramsByTitle(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Program p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    int countProgramsByTitle(@Param("keyword") String keyword);

    List<Program> findByCategoryTitle(String categoryTitle);

    List<Program> findTop3ByCategoryIdInOrderByStartDateDesc(List<Long> categoryIds);

    List<Program> findTop3ByOrderByStartDateDesc();

    // 카테고리 제목에 해당하는 프로그램 목록 조회 (pageable 추가)
    List<Program> findByCategoryTitle(String categoryTitle, Pageable pageable);

    // 제목에 키워드를 포함하는 프로그램들 검색
    List<Program> findByTitleContaining(String keyword, Pageable pageable);

    // 제목에 키워드를 포함하는 프로그램의 총 개수 반환
    long countByTitleContaining(String keyword);
}
