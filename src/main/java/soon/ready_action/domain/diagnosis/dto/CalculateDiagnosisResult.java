package soon.ready_action.domain.diagnosis.dto;

import lombok.Builder;

@Builder
public record CalculateDiagnosisResult(

    String categoryTitle,

    int score
) {

}
