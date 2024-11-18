package soon.ready_action.global.oauth2.service;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import soon.ready_action.domain.member.entity.Member;
import soon.ready_action.domain.member.repository.MemberRepository;
import soon.ready_action.global.oauth2.dto.CustomOAuth2Member;

@Slf4j
@Service
@RequiredArgsConstructor
public class Oauth2KakaoService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String kakaoId = String.valueOf(oAuth2User.getAttributes().get("id"));
        Member member = memberRepository.findByKakaoId(kakaoId);

        if (member == null) {
            member = Member.builder()
                .kakaoId(kakaoId)
                .build();

            memberRepository.save(member);
        }

        return new CustomOAuth2Member(
            Collections.singleton(new SimpleGrantedAuthority(member.getRole().name())),
            oAuth2User.getAttributes(),
            "id",
            member.getRole(),
            member.getId()
        );
    }
}
