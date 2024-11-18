package soon.ready_action.global.oauth2.jwt.filter;

import static soon.ready_action.global.oauth2.jwt.common.TokenType.AUTHORIZATION_HEADER;
import static soon.ready_action.global.oauth2.jwt.common.TokenType.BEARER_PREFIX;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import soon.ready_action.global.oauth2.jwt.provider.TokenProvider;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String tokenFromHeader = getTokenFromHeader(request);

        if (tokenFromHeader == null || !tokenProvider.validateToken(tokenFromHeader)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return;
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenFromHeader);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String tokenValue = request.getHeader(AUTHORIZATION_HEADER.getValue());

        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX.getValue())) {
            return tokenValue.substring(BEARER_PREFIX.getValue().length());
        }

        return null;
    }
}

