package soon.ready_action.domain.house.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import soon.ready_action.domain.house.dto.request.HouseFilterRequest;
import soon.ready_action.domain.house.dto.response.HouseResponse;
import soon.ready_action.global.exception.dto.response.ErrorResponse;

@Tag(description = "House API", name = "House Controller")
public abstract class HouseDocsController {

    @Operation(summary = "주거 조회", description = "입력한 시/군/구에 해당하는 주거를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "주거 조회 성공", content = @Content(schema = @Schema(implementation = HouseResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public abstract ResponseEntity<List<HouseResponse>> handleHouse(HouseFilterRequest request);
}
