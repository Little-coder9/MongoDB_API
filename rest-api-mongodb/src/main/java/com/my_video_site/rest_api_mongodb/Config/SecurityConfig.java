package com.my_video_site.rest_api_mongodb.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->
                        auth.requestMatchers(
                                        "/Movies/**",
                                        "/movie/{id}",            //  for movie endpoints
                                        "/searchMovies",
                                        "/featuredMovies/**",
                                        "/addMovie",
                                        "/updateMovie/{id}",
                                        "/deleteMovie/{id}",
                                        "/users/**",              // for user endpoints
                                        "/user/{id}",
                                        "/register",
                                        "/authenticate",
                                        "/TVShows/**",            //for tv endpoints
                                        "/tv/{id}",
                                        "/searchTv",
                                        "/featuredTv",
                                        "/addTv",
                                        "/updateTv/{id}",
                                        "/deleteTv/{id}",
                                        "/error",
                                        "/authenticate"
                                ).permitAll()
                                .anyRequest().authenticated()
                                .and()
                                .addFilterBefore(new JwtFilter(jwtUtil()), UsernamePasswordAuthenticationFilter.class)

                );
        return httpSecurity.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
