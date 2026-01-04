package com.secj3303.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {

        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin1234")
                .roles("ADMIN", "MEMBER")
                .build();

        UserDetails member = User.withUsername("member")
                .password("{noop}member1234")
                .roles("MEMBER")
                .build();

        UserDetails trainer = User.withUsername("trainer")
                .password("{noop}trainer1234")
                .roles("TRAINER")
                .build();

        return new InMemoryUserDetailsManager(admin, member, trainer);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf().disable()

            .authorizeHttpRequests()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/member/**").hasRole("MEMBER")
                .requestMatchers("/trainer/**").hasRole("TRAINER")
                .anyRequest().authenticated()
                .and()

            .formLogin()
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
                .and()

            .logout()
                .logoutSuccessUrl("/login?logout")
                .permitAll();

        return http.build();
    }
}

