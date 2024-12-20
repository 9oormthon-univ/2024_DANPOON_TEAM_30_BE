package soon.ready_action.domain.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import soon.ready_action.domain.program.dto.response.ProgramResponse;
import soon.ready_action.domain.program.dto.response.ProgramResponse.ProgramContent;
import soon.ready_action.domain.knowledge.dto.KnowledgeResponse.KnowledgeContent;

import java.util.List;

@Data
@AllArgsConstructor
public class MainResponse {
    private List<ProgramResponse.DetailResponse> programs;
    private List<KnowledgeContent> knowledge;
    private String selfDiagnosis;
}
