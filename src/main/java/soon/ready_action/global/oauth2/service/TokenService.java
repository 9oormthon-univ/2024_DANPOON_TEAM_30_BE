package soon.ready_action.global.oauth2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.ready_action.domain.member.entity.Role;
import soon.ready_action.domain.member.service.MemberService;
import soon.ready_action.global.oauth2.dto.CustomOAuth2Member;
import soon.ready_action.global.oauth2.jwt.dto.response.TokenResponse;
import soon.ready_action.global.oauth2.jwt.provider.TokenProvider;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;
    private final MemberService memberService;

    @Transactional
    public TokenResponse handleTokenGenerationAndUpdate(CustomOAuth2Member oAuth2Member) {
        Role role = oAuth2Member.getRole();
        Long memberId = oAuth2Member.getMemberId();

        TokenResponse tokenResponse = tokenProvider.generateAllToken(memberId, role);

        String refreshToken = tokenResponse.refreshToken();
        memberService.updateRefreshToken(refreshToken, memberId);

        return tokenResponse;
    }
}
