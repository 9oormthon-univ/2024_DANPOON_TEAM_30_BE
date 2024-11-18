package soon.ready_action.global.oauth2.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import soon.ready_action.domain.member.entity.Role;
import soon.ready_action.global.oauth2.dto.CustomOAuth2Member;
import soon.ready_action.global.oauth2.jwt.provider.TokenProvider;

@Slf4j
@RequiredArgsConstructor
@Component
public class Oauth2KakaoSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        log.info("onAuthenticationSuccess");

        CustomOAuth2Member oAuth2Member = (CustomOAuth2Member) authentication.getPrincipal();
        Role role = oAuth2Member.getRole();
        Long memberId = oAuth2Member.getMemberId();
    }
}
