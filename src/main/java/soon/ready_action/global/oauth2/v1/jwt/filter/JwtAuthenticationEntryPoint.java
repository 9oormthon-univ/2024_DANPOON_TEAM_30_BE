package soon.ready_action.global.oauth2.v1.jwt.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import soon.ready_action.global.exception.dto.response.ErrorResponse;
import soon.ready_action.global.provider.CustomObjectMapperProvider;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final CustomObjectMapperProvider objectMapperProvider;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
        log.error("인증 실패 - 요청 URI: {}, 에러 메시지: {}", request.getRequestURI(),
            authException.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpServletResponse.SC_UNAUTHORIZED)
            .message("인증 실패")
            .build();

        String jsonResponse = objectMapperProvider.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }
}
