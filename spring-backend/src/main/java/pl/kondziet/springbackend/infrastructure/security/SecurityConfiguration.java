package pl.kondziet.springbackend.infrastructure.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import pl.kondziet.springbackend.infrastructure.security.token.TokenVerifyFilter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Configuration
public class SecurityConfiguration {

    private final TokenVerifyFilter tokenVerifyFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors().configurationSource(request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
            corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
            corsConfiguration.setAllowedHeaders(List.of("*"));
            corsConfiguration.setExposedHeaders(List.of("Authorization"));
            corsConfiguration.setAllowCredentials(true);
            return corsConfiguration;
        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);

        httpSecurity.authorizeHttpRequests(request -> request
                .requestMatchers("api/authentication/**")
                .permitAll()
                .anyRequest()
                .authenticated()
        );

        httpSecurity
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(tokenVerifyFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }

}
