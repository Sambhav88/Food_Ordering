package com.Sambhav.FoodOrdering.Config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class AppConfig
{
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception
    {
        http.sessionManagement(management-> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize->Authorize
                        .requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER","ADMIN")
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                ).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf->csrf.disable())
                .cors(cors->cors.configurationSource(corsConfigrationSource()));

        return http.build();
    }

    private CorsConfigurationSource corsConfigrationSource()
    {
        return new CorsConfigurationSource()
        {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request)
            {
                return null;
            }
        };
    }
    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
