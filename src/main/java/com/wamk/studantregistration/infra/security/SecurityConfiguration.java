package com.wamk.studantregistration.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private SecurityFilter securityFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						//AUTHENTICATION
						.requestMatchers(new AntPathRequestMatcher("/auth/login", HttpMethod.POST.toString())).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/auth/register", HttpMethod.POST.toString())).permitAll()
						//STUDENTS
						.requestMatchers(new AntPathRequestMatcher("/students", HttpMethod.POST.toString())).hasRole("ADMIN")
						.requestMatchers(new AntPathRequestMatcher("/students", HttpMethod.GET.toString())).hasRole("ADMIN")
						.requestMatchers(new AntPathRequestMatcher("/students/{id}", HttpMethod.GET.toString())).hasAnyRole("ADMIN", "USER")
						.requestMatchers(new AntPathRequestMatcher("/students/{id}", HttpMethod.PUT.toString())).hasRole("ADMIN")
						.requestMatchers(new AntPathRequestMatcher("/students/{id}", HttpMethod.DELETE.toString())).hasRole("ADMIN")
						.anyRequest().authenticated()
						)
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}
