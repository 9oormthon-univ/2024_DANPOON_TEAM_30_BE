package soon.ready_action.domain.diagnosis.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soon.ready_action.domain.diagnosis.dto.response.DiagnosisQuestionResponse;

@SpringBootTest
class DiagnosisQuestionServiceTest {

    @Autowired
    private DiagnosisQuestionService diagnosisQuestionService;

    @Test
    void a() {
        // given
        List<DiagnosisQuestionResponse> numberingPagination = diagnosisQuestionService.getNumberingPagination(
            4);

        numberingPagination.forEach(System.out::println);

        // when

        // then
    }
}