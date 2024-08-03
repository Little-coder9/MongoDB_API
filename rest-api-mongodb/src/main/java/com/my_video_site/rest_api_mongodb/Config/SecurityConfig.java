package com.my_video_site.rest_api_mongodb.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/Movies/**",
                                        "/{id}",
                                        "/featured",
                                        "/movieSearch",
                                        "/Movie/",
                                        "/Users/**",
                                        "/Tvs",
                                        "/tvSearch",
                                        "/addTVShow",
                                        "/tvFeatured",
                                        "/tv/{id}",
                                        "/TVShows/**",
                                        "/error"
                                ).permitAll()
                        .anyRequest().authenticated()
                );
                return httpSecurity.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
