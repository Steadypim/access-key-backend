package com.example.accesskeybackend.security;

import com.example.accesskeybackend.props.SecurityProperties;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(1)
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "authenticated", scheme = "Basic", description = "Need auth")
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain filterChain(final HttpSecurity http, final SecurityProperties props) throws Exception {
        return http
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/api/public/**").permitAll()
                                .requestMatchers("/actuator/health").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    protected UserDetailsService userDetailsService(final PasswordEncoder passwordEncoder,
                                                    final SecurityProperties props) {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username(props.getAdminUser())
                        .password(props.getAdminPwd())
                        .passwordEncoder(passwordEncoder::encode)
                        .roles("ADMIN")
                        .build()
        );
    }
}
