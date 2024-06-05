package com.example.SpringBootProject.config;

import com.example.SpringBootProject.repository.UserRepository;
import com.example.SpringBootProject.util.security.JwtAuthFilter;
import com.example.SpringBootProject.util.security.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig{

    private final JwtAuthFilter jwtAuthFilter;

    private static final String[] AUTH_WHITELIST = {
            "/auth/signin",
            "/auth/signup",
            "/user/**",
            "/product/**",
            "/cart/**",
            "/order/**",
    };
    private static final String[] FOR_ADMINS_LIST = {
            "/admin/**",
            "/user/getPage",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new UserInfoService(userRepository);
    }

    @Bean
    public AuthenticationProvider authenticationProvider (UserDetailsService userDetailsService,
                                                          PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                (authz)->authz.requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers(FOR_ADMINS_LIST).hasAuthority("ADMIN")
                        .anyRequest().authenticated()
        ).httpBasic(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
         .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
         .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
         .build();
    }

}
