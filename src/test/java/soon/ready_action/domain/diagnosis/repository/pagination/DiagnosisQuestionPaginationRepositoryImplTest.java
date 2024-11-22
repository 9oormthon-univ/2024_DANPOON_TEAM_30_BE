package soon.ready_action.domain.diagnosis.repository.pagination;

import static org.assertj.core.api.Assertions.assertThat;
import static soon.ready_action.domain.diagnosis.repository.pagination.DiagnosisQuestionPaginationRepository.PAGE_SIZE;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soon.ready_action.domain.diagnosis.dto.response.DiagnosisQuestionResponse;
import soon.ready_action.domain.diagnosis.repository.jpa.DiagnosisQuestionJpaRepository;

@SpringBootTest
class DiagnosisQuestionPaginationRepositoryImplTest {

    @Autowired
    private DiagnosisQuestionJpaRepository diagnosisQuestionJpaRepository;

    @Test
    void getPagedDiagnosisQuestion() {
        // given
        Long memberId = 1L;
        Long lastId = null;
        String categoryTitle = "주거";

        // when
        List<DiagnosisQuestionResponse> pagedDiagnosisQuestion = diagnosisQuestionJpaRepository.getPagedDiagnosisQuestion(
            lastId, memberId, categoryTitle);

        // then
        DiagnosisQuestionResponse diagnosisQuestionResponse = pagedDiagnosisQuestion.get(0);
        assertThat(diagnosisQuestionResponse.category()).isEqualTo(categoryTitle);
        assertThat(diagnosisQuestionResponse.answerType()).isNull();
        assertThat(pagedDiagnosisQuestion.size()).isEqualTo(PAGE_SIZE + 1);
    }
}