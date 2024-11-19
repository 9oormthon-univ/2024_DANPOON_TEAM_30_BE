package soon.ready_action.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.ready_action.domain.member.dto.request.MemberAdditionalInfoRequest;
import soon.ready_action.domain.member.entity.Member;
import soon.ready_action.domain.member.entity.Role;
import soon.ready_action.domain.member.repository.MemberRepository;
import soon.ready_action.domain.member.service.MemberService;
import soon.ready_action.global.exception.ForbiddenException;
import soon.ready_action.global.oauth2.jwt.dto.request.ReissueTokenRequest;
import soon.ready_action.global.oauth2.jwt.dto.response.TokenResponse;
import soon.ready_action.global.oauth2.jwt.provider.TokenProvider;
import soon.ready_action.global.oauth2.service.TokenService;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public TokenResponse reissueToken(ReissueTokenRequest request) {
        String accessToken = request.accessToken();
        String refreshToken = request.refreshToken();

        Authentication auth = tokenProvider.getAuthentication(accessToken);
        Long memberId = Long.valueOf(auth.getName());
        Member member = memberRepository.findById(memberId);

        if (!member.isEqualsRefreshToken(refreshToken)) {
            throw new JwtException("invalid refreshToken");
        }

        TokenResponse tokenResponse = tokenProvider.reissueToken(memberId, member.getRole(),
            refreshToken);
        member.updateRefreshToken(tokenResponse.refreshToken());

        return tokenResponse;
    }

    @Transactional
    public TokenResponse signup(MemberAdditionalInfoRequest request) {
        Long loginMemberId = TokenService.getLoginMemberId();
        Member member = memberRepository.findById(loginMemberId);

        if (!Role.isGuest(member.getRole())) {
            throw new ForbiddenException();
        }

        TokenResponse tokenResponse = tokenProvider.reissueToken(member.getId(), member.getRole(),
            member.getRefreshToken());

        member.updateAdditionalInfo(
            request.nickname(),
            request.birthday(),
            tokenResponse.refreshToken(),
            Role.ROLE_MEMBER
        );

        return tokenResponse;
    }
}
