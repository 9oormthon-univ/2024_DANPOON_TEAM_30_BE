package soon.ready_action.global.oauth2.jwt.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import soon.ready_action.global.exception.dto.response.ErrorResponse;
import soon.ready_action.global.provider.CustomObjectMapperProvider;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final CustomObjectMapperProvider objectMapperProvider;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("유효하지 않은 접근: {}", accessDeniedException.getMessage());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(HttpServletResponse.SC_FORBIDDEN)
            .message("유효하지 않은 접근 : " + accessDeniedException.getMessage())
            .build();

        String jsonResponse = objectMapperProvider.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }
}
