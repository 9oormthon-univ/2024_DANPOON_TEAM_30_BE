package soon.ready_action.domain.house.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soon.ready_action.domain.house.dto.response.HouseResponse;
import soon.ready_action.domain.house.service.HouseService;
import soon.ready_action.global.exception.dto.response.ErrorResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/houses")
@RequiredArgsConstructor
@Tag(name = "House Controller", description = "House API")
public class HouseController {

    private final HouseService houseService;

    @Operation(summary = "주거 매물 조회", description = "시/군/구에 따른 주거 매물 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = HouseResponse.class))),
            @ApiResponse(responseCode = "404", description = "잘못된 경로", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public List<HouseResponse> getHousesByRegion(
            @RequestParam("region1depthName") String region1depthName,
            @RequestParam("region2depthName") String region2depthName) {
        return houseService.getHousesByRegion(region1depthName, region2depthName);
    }
}
