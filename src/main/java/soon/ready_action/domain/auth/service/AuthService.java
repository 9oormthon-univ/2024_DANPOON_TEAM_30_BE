package soon.ready_action.domain.auth.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.ready_action.domain.auth.dto.response.OnboardingWrapperResponse;
import soon.ready_action.domain.diagnosis.dto.response.OnboardingQuestionResponse;
import soon.ready_action.domain.diagnosis.service.DiagnosisService;
import soon.ready_action.domain.member.dto.request.MemberAdditionalInfoRequest;
import soon.ready_action.domain.member.entity.Member;
import soon.ready_action.domain.member.entity.Role;
import soon.ready_action.domain.member.repository.MemberRepository;
import soon.ready_action.domain.membercategory.service.MemberCategoryService;
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
    private final MemberCategoryService memberCategoryService;
    private final DiagnosisService diagnosisService;

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
    public OnboardingWrapperResponse signup(MemberAdditionalInfoRequest request) {
        Long loginMemberId = TokenService.getLoginMemberId();
        Member member = memberRepository.findById(loginMemberId);

        if (!Role.isGuest(member.getRole())) {
            throw new ForbiddenException();
        }

        // 토큰 권한 초기화를 위한 재발급
        TokenResponse tokenResponse = tokenProvider.reissueToken(member.getId(), member.getRole(),
            member.getRefreshToken());

        member.updateAdditionalInfo(
            request.nickname(),
            request.birthday(),
            tokenResponse.refreshToken(),
            Role.ROLE_MEMBER
        );

        List<String> categories = request.categories();
        memberCategoryService.associateMemberWithCategories(categories, member);

        List<OnboardingQuestionResponse> onboardingQuestionResponseByCategories = diagnosisService
            .getOnboardingQuestionResponseByCategories(categories);

        return OnboardingWrapperResponse.builder()
            .tokenResponse(tokenResponse)
            .onboardingQuestionResponse(onboardingQuestionResponseByCategories)
            .build();
    }
}
