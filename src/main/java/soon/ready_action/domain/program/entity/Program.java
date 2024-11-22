package soon.ready_action.domain.program.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soon.ready_action.domain.category.entity.Category;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private ProgramStatus status;

    @Column(nullable = false)
    private String applicationUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // 오늘 날짜에 맞게 모집 상태를 계산하여 반환
    public ProgramStatus getProgramStatus() {
        LocalDate today = LocalDate.now();

        if (startDate.isBefore(today) && endDate.isAfter(today)) {
            return ProgramStatus.RECRUITING; // 모집 중
        } else if (endDate.isBefore(today)) {
            return ProgramStatus.COMPLETED; // 모집 종료
        }

        return ProgramStatus.RECRUITING; // 기본값은 모집 중
    }
}
