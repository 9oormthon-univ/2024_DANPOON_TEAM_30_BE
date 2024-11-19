package soon.ready_action.domain.diagnosis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import soon.ready_action.domain.diagnosis.repository.DiagnosisCategoryScoreRepository;
import soon.ready_action.domain.diagnosis.repository.DiagnosisQuestionRepository;
import soon.ready_action.domain.diagnosis.repository.DiagnosisResultRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiagnosisService {

    private final DiagnosisQuestionRepository questionRepository;
    private final DiagnosisResultRepository resultRepository;
    private final DiagnosisCategoryScoreRepository scoreRepository;

}
