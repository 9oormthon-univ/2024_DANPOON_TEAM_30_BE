package soon.ready_action.global.oauth2.v1.dto;

import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import soon.ready_action.domain.member.entity.Role;

@Getter
public class CustomOAuth2Member extends DefaultOAuth2User {

    private final Role role;
    private final Long memberId;

    public CustomOAuth2Member(Collection<? extends GrantedAuthority> authorities,
        Map<String, Object> attributes, String nameAttributeKey, Role role, Long memberId) {
        super(authorities, attributes, nameAttributeKey);

        this.role = role;
        this.memberId = memberId;
    }
}
