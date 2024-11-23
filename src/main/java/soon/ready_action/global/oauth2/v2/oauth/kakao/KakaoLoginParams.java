//package soon.ready_action.global.oauth2.v2.oauth.kakao;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import soon.ready_action.domain.member.entity.SocialType;
//import soon.ready_action.global.oauth2.v2.oauth.common.OAuthLoginParams;
//
//@Getter
//@NoArgsConstructor
//public class KakaoLoginParams implements OAuthLoginParams {
//    private String authorizationCode;
//
//    @Override
//    public SocialType oAuthProvider() {
//        return SocialType.KAKAO;
//    }
//
//    @Override
//    public MultiValueMap<String, String> makeBody() {
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("code", authorizationCode);
//        return body;
//    }
//}