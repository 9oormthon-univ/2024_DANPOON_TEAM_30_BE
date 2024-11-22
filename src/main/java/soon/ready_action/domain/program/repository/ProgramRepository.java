package soon.ready_action.domain.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import soon.ready_action.domain.program.entity.Program;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {

    // 카테고리 ID와 lastProgramId를 기준으로 프로그램 목록 조회
    @Query("SELECT p FROM Program p WHERE p.category.id = :categoryId AND p.id < :lastProgramId ORDER BY p.id DESC")
    List<Program> findProgramsAfterId(@Param("categoryId") Long categoryId, @Param("lastProgramId") Long lastProgramId, Pageable pageable);

    // 카테고리 ID로 첫 번째 페이지의 프로그램 목록 조회
    @Query("SELECT p FROM Program p WHERE p.category.id = :categoryId ORDER BY p.id DESC")
    List<Program> findFirstPrograms(@Param("categoryId") Long categoryId, Pageable pageable);

    // 검색
    @Query("SELECT p FROM Program p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY p.id DESC")
    List<Program> searchProgramsByTitle(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Program p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    int countProgramsByTitle(@Param("keyword") String keyword);
}
