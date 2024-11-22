package soon.ready_action.domain.diagnosis.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soon.ready_action.domain.category.entity.Category;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DiagnosisQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_question_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private boolean isOnboardingQuestion;

    @Column(nullable = false)
    private boolean isOnboardingRelation;

    @Builder
    public DiagnosisQuestion(String content, Category category, boolean isOnboardingQuestion,
        boolean isOnboardingRelation) {
        this.content = content;
        this.category = category;
        this.isOnboardingQuestion = isOnboardingQuestion;
        this.isOnboardingRelation = isOnboardingRelation;
    }
}
