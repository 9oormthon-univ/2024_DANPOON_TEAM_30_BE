package soon.ready_action.global.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final Oauth2KakaoSuccessHandler oauth2KakaoSuccessHandler;
    private final Oauth2KakaoFailHandler oauth2KakaoFailHandler;
    private final Oauth2KakaoService oauth2KakaoService;

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
            .oauth2Login(oauth2 -> {
                oauth2
                    .userInfoEndpoint(
                        (userInfoEndpointConfig) ->
                            userInfoEndpointConfig.userService(oauth2KakaoService))
                    .successHandler(oauth2KakaoSuccessHandler)
                    .failureHandler(oauth2KakaoFailHandler);
            });

        http
            .authorizeHttpRequests(auth -> {
                auth
                    .requestMatchers(HttpMethod.PUT, "/api/v1/signup").hasAuthority("ROLE_GUEST")
                    .anyRequest().permitAll();
            })
            .addFilterBefore(new JwtAuthorizationFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> webSecurity.ignoring()
            .requestMatchers("/swagger-ui/**", "/favicon.ico", "/api-docs/**");
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
