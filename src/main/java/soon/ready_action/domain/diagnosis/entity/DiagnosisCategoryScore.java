package soon.ready_action.domain.diagnosis.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DiagnosisCategoryScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_category_score_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    private int score = 0;

    @Builder
    public DiagnosisCategoryScore(Long memberId, Long categoryId, int score) {
        this.memberId = memberId;
        this.categoryId = categoryId;
        this.score = score;
    }

    public void updateScore(int score) {
        this.score = score;
    }
}
