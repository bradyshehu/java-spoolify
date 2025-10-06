package org.lessons.spoolify.java.java_spoolify.security;

import org.lessons.spoolify.java.java_spoolify.services.DBUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    DBUserDetailsService userdetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
        .authorizeHttpRequests(
            auth -> auth
            .requestMatchers("/albums/create", "/albums/edit/**", "/albums/*/songs").hasAuthority("ADMIN") //CREATE ALBUM, EDIT ALBUM, CREATE SONG
            .requestMatchers(HttpMethod.POST, "/albums/**").hasAuthority("ADMIN") // POST METHODS PER ALBUM(DELETE)
            .requestMatchers("/songs/edit/**").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.POST, "/songs/**").hasAuthority("ADMIN") // POST METHODS PER SONG(DELETE)
            .requestMatchers("/genres/create", "/genres/edit/**").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.POST, "/genres/**").hasAuthority("ADMIN") // POST METHODS PER GENRE(DELETE)
            .requestMatchers("/", "/home", "/albums", "/albums/**", "/songs", "/songs/**", "/genres", "/genres/**").hasAnyAuthority("USER", "ADMIN")
            .anyRequest().authenticated()
            )
            .formLogin(
            form -> form
            .defaultSuccessUrl("/", true)
            .permitAll()
            )
            .logout(
            logout -> logout
            .logoutUrl("/logout")                   
            .logoutSuccessUrl("/login")      
            .invalidateHttpSession(true)            
            .clearAuthentication(true)        
            .permitAll()             
            );

            return http.build();
    }

    @Bean
    public PasswordEncoder pwEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @SuppressWarnings("deprecation")
    public DaoAuthenticationProvider authProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(pwEncoder());
        authProvider.setUserDetailsService(userdetailsService);
        return authProvider;
    }
    
}
