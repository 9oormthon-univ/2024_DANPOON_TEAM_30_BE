package soon.ready_action.domain.diagnosis.repository.pagination;

import java.util.List;
import soon.ready_action.domain.diagnosis.dto.response.DiagnosisQuestionResponse;

public interface DiagnosisQuestionPaginationRepository {

    int PAGE_SIZE = 5;

    List<DiagnosisQuestionResponse> getPagedDiagnosisQuestion(
        Long lastId,
        Long memberId,
        String categoryTitle
    );

    List<DiagnosisQuestionResponse> getNumberingPagination(
        int page
    );

    boolean determineHasNextPage(List<DiagnosisQuestionResponse> paginatedDiagnosisQuestion);
}
