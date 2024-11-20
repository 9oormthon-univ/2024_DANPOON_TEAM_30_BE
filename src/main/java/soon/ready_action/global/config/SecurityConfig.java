package soon.ready_action.global.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import soon.ready_action.global.oauth2.handler.Oauth2KakaoFailHandler;
import soon.ready_action.global.oauth2.handler.Oauth2KakaoSuccessHandler;
import soon.ready_action.global.oauth2.jwt.filter.JwtAuthorizationFilter;
import soon.ready_action.global.oauth2.jwt.provider.TokenProvider;
import soon.ready_action.global.oauth2.service.Oauth2KakaoService;
import soon.ready_action.global.provider.CustomObjectMapperProvider;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final Oauth2KakaoSuccessHandler oauth2KakaoSuccessHandler;
    private final Oauth2KakaoFailHandler oauth2KakaoFailHandler;
    private final Oauth2KakaoService oauth2KakaoService;
    private final CustomObjectMapperProvider customObjectMapperProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .cors(cors -> cors.configurationSource(corsConfig()));

        http
            .authorizeHttpRequests(auth -> {
                auth
                    .requestMatchers(
                        "/oauth2/**", "/login/oauth2/**", "/api/v1/auth/reissue", "/swagger-ui/**",
                        "/v3/api-docs/**"
                    ).permitAll()
                    .requestMatchers(HttpMethod.PUT, "/api/v1/auth/signup")
                    .hasAuthority("ROLE_GUEST")
                    .anyRequest().authenticated();
            });

        http
            .oauth2Login(oauth2 -> {
                oauth2
                    .userInfoEndpoint(userInfoEndpointConfig ->
                        userInfoEndpointConfig.userService(oauth2KakaoService))
                    .successHandler(oauth2KakaoSuccessHandler)
                    .failureHandler(oauth2KakaoFailHandler);
            });

        http
            .addFilterBefore(new JwtAuthorizationFilter(tokenProvider, customObjectMapperProvider),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfig() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of("http://localhost:3000", "https://domain.com"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
