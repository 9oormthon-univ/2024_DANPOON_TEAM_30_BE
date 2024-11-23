package soon.ready_action.domain.main.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import soon.ready_action.domain.main.service.MainService;
import soon.ready_action.domain.main.dto.response.MainResponse;
import soon.ready_action.global.exception.dto.response.ErrorResponse;
import soon.ready_action.global.oauth2.v1.service.TokenService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Main Controller", description = "Main API")
public class MainController {

    private final MainService mainService;

    @Operation(summary = "메인 페이지 조회", description = "메인 페이지 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = MainResponse.class))),
            @ApiResponse(responseCode = "404", description = "잘못된 경로", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/api/v1/main")
    public MainResponse getMainPage() {
        Long memberId = TokenService.getLoginMemberId();

        return mainService.getMainPage(memberId);
    }
}
