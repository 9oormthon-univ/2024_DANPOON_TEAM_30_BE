package soon.ready_action.domain.diagnosis.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DiagnosisScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_score_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private int healthScore = 0;

    @Column(nullable = false)
    private int residenceScore = 0;

    @Column(nullable = false)
    private int educationScore = 0;

    @Column(nullable = false)
    private int financeScore = 0;

    @Column(nullable = false)
    private int employmentScore = 0;

}
