package soon.ready_action.global.oauth2.v1.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import soon.ready_action.domain.auth.dto.response.AuthResponse;
import soon.ready_action.domain.member.entity.Role;
import soon.ready_action.global.oauth2.v1.dto.CustomOAuth2Member;
import soon.ready_action.global.oauth2.v1.jwt.dto.response.TokenResponse;
import soon.ready_action.global.oauth2.v1.service.TokenService;
import soon.ready_action.global.provider.CustomObjectMapperProvider;

@Slf4j
@RequiredArgsConstructor
@Component
public class Oauth2KakaoSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final CustomObjectMapperProvider customObjectMapperProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        log.info("OAuth2 Success Handler");
        CustomOAuth2Member oAuth2Member = extractOAuth2Member(authentication);

        TokenResponse tokenResponse = tokenService.handleTokenGenerationAndUpdate(oAuth2Member);
        String redirectUrl = determineRedirectUrl(oAuth2Member);

        AuthResponse authResponse = getAuthResponse(tokenResponse, redirectUrl);
        response(response, authResponse);
    }

    private AuthResponse getAuthResponse(TokenResponse tokenResponse, String redirectUrl) {
        return AuthResponse.builder()
            .accessToken(tokenResponse.accessToken())
            .refreshToken(tokenResponse.refreshToken())
            .redirectURL(redirectUrl)
            .build();
    }

    private void response(HttpServletResponse response, AuthResponse authResponse)
        throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter()
            .write(customObjectMapperProvider.getObjectMapper().writeValueAsString(authResponse)
            );
    }

    private CustomOAuth2Member extractOAuth2Member(Authentication authentication) {
        return (CustomOAuth2Member) authentication.getPrincipal();
    }


    private String determineRedirectUrl(CustomOAuth2Member oAuth2Member) {
        // TODO 도메인 구매 시 변경 + 프론트 구현 시 변경
        String baseUrl = "https://www.domain.com";
        String path = Role.isGuest(oAuth2Member.getRole()) ? "/info" : "/";

        return UriComponentsBuilder.fromUriString(baseUrl + path).build().toUriString();
    }

}
