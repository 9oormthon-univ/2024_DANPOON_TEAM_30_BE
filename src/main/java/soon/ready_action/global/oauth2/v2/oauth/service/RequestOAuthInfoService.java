//package soon.ready_action.global.oauth2.v2.oauth.service;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestClientException;
//
//import java.util.List;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//import soon.ready_action.domain.member.entity.SocialType;
//import soon.ready_action.global.oauth2.v2.oauth.common.OAuthApiClient;
//import soon.ready_action.global.oauth2.v2.oauth.common.OAuthInfoResponse;
//import soon.ready_action.global.oauth2.v2.oauth.common.OAuthLoginParams;
//import soon.ready_action.global.oauth2.v2.oauth.exception.OAuthException;
//
//@Component
//public class RequestOAuthInfoService {
//    private final Map<SocialType, OAuthApiClient> clients;
//
//    public RequestOAuthInfoService(List<OAuthApiClient> clients) {
//        this.clients = clients.stream().collect(
//            Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())
//        );
//    }
//
//    public OAuthInfoResponse request(OAuthLoginParams params) throws OAuthException {
//        try {
//            OAuthApiClient client = clients.get(params.oAuthProvider());
//            String accessToken = client.requestAccessToken(params);
//            return client.requestOauthInfo(accessToken);
//        } catch (RestClientException e) {
//            throw new OAuthException();
//        }
//    }
//}