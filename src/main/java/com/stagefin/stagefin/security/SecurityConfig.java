package com.stagefin.stagefin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    public UserDetailsService inMemoryUserDetailsManager(){
        return new InMemoryUserDetailsManager(
                User.withUsername("admin").password(passwordEncoder.encode("1234")).
                        roles("ADMIN").build(),
                User.withUsername("user").password(passwordEncoder.encode("1234")).
                        roles("USR").build()

        )    ;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.formLogin().loginPage("/login") //.defaultSuccessUrl("/template1")
         .permitAll();
        httpSecurity.rememberMe();
        httpSecurity.authorizeHttpRequests().requestMatchers("/webjars/**","/h2-console/**").permitAll();

        httpSecurity.authorizeHttpRequests().requestMatchers("/usr/**").hasRole("USR");
        httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
       // httpSecurity.authorizeHttpRequests().requestMatchers("/template1").authenticated();



        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
        httpSecurity.exceptionHandling().accessDeniedPage("/notAuthorized");
        return httpSecurity.build();
    }}
