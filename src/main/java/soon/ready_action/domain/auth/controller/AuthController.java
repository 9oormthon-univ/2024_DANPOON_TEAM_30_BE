package soon.ready_action.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soon.ready_action.domain.auth.dto.response.OnboardingWrapperResponse;
import soon.ready_action.domain.auth.service.AuthService;
import soon.ready_action.domain.member.dto.request.MemberAdditionalInfoRequest;
import soon.ready_action.global.oauth2.jwt.dto.request.ReissueTokenRequest;
import soon.ready_action.global.oauth2.jwt.dto.response.TokenResponse;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController extends AuthDocsController {

    private final AuthService authService;

    @Override
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissueToken(
        @RequestBody ReissueTokenRequest reissueTokenRequest) {
        TokenResponse tokenResponse = authService.reissueToken(reissueTokenRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    @Override
    @PutMapping("/signup")
    public ResponseEntity<OnboardingWrapperResponse> signup(@RequestBody MemberAdditionalInfoRequest request) {
        OnboardingWrapperResponse onboardingWrapperResponse = authService.signup(request);

        return ResponseEntity.ok(onboardingWrapperResponse);
    }
}
