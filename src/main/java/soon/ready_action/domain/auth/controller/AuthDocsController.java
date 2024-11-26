package soon.ready_action.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import soon.ready_action.domain.auth.dto.response.AuthResponse;
import soon.ready_action.domain.auth.dto.response.OnboardingWrapperResponse;
import soon.ready_action.domain.member.dto.request.MemberAdditionalInfoRequest;
import soon.ready_action.global.exception.dto.response.ErrorResponse;
import soon.ready_action.global.oauth2.jwt.dto.request.ReissueTokenRequest;
import soon.ready_action.global.oauth2.jwt.dto.response.TokenResponse;

@Tag(name = "Auth Controller", description = "Auth API")
public abstract class AuthDocsController {


    @Operation(summary = "토큰 재발급", description = "만료된 accessToken 재발급")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "토큰 재발급 성공", content = @Content(schema = @Schema(implementation = TokenResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public abstract ResponseEntity<TokenResponse> reissueToken(ReissueTokenRequest request);

    @Operation(summary = "카카오 로그인", description = "카카오 소셜 로그인")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/oauth2/authorization/kakao")
    public void kakao() {
    }

    @Operation(summary = "추가정보 입력", description = "추가정보 입력")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "추가 정보 입력 성공 시 권한이 바뀐 새로운 토큰 반환 및 관심카테고리에 따른 질문 응답", content = @Content(schema = @Schema(implementation = OnboardingWrapperResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "request 값이 잘못된 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public abstract ResponseEntity<OnboardingWrapperResponse> signup(
        MemberAdditionalInfoRequest request
    );
}
