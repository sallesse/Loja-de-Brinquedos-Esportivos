package br.edu.ibmec.loja_de_brinquedos_esportivos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/brinquedos/**"))

                .authorizeHttpRequests(auth -> auth.
                        requestMatchers("/", "/login", "/error").permitAll()

                        .requestMatchers(HttpMethod.GET, "/brinquedo/**").hasAnyRole("USER", "ADMIN")

                        .requestMatchers("/brinquedos/**").hasRole("ADMIN")

                        .anyRequest().authenticated())

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/painel", true)
                        .permitAll())
                .httpBasic(Customizer.withDefaults())

                .logout(logout -> logout.logoutSuccessUrl("/"));
        return http.build();






    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
