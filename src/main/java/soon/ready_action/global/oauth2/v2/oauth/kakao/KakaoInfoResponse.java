//package soon.ready_action.global.oauth2.v2.oauth.kakao;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Getter;
//import soon.ready_action.domain.member.entity.SocialType;
//import soon.ready_action.global.oauth2.v2.oauth.common.OAuthInfoResponse;
//
//@Getter
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class KakaoInfoResponse implements OAuthInfoResponse {
//
//    @JsonProperty("kakao_account")
//    private KakaoAccount kakaoAccount;
//
//    @Getter
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    static class KakaoAccount {
//        private KakaoProfile profile;
//        private String email;
//    }
//
//    @Getter
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    static class KakaoProfile {
//        private String nickname;
//    }
//
//    @Override
//    public String getEmail() {
//        return kakaoAccount.email;
//    }
//
//    @Override
//    public String getNickname() {
//        return kakaoAccount.profile.nickname;
//    }
//
//    @Override
//    public SocialType getOAuthProvider() {
//        return SocialType.KAKAO;
//    }
//}