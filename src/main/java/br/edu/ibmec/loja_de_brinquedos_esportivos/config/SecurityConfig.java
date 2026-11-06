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
                .csrf(csrf -> csrf.ignoringRequestMatchers("/brinquedo/**"))

                // As regras são avaliadas na ordem: da mais específica para a mais genérica.
                .authorizeHttpRequests(auth -> auth
                        // Rotas públicas (somente estas!)
                        .requestMatchers("/", "/login", "/error", "/css/**").permitAll()

                        .requestMatchers("/admin", "/admin/**",
                                "/painel/admin", "/painel/admin/**",
                                "/painel/brinquedos", "/painel/brinquedos/**",
                                "/h2-console/**").hasRole("ADMIN")

                        // Painel: exige login (USER ou ADMIN)
                        .requestMatchers("/painel", "/painel/**").hasAnyRole("USER", "ADMIN")

                        // API REST: leitura para USER/ADMIN, escrita só ADMIN
                        .requestMatchers(HttpMethod.GET, "/brinquedo", "/brinquedo/**")
                        .hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/brinquedo", "/brinquedo/**").hasRole("ADMIN")

                        // Qualquer outra rota: precisa estar logado
                        .anyRequest().authenticated())

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/painel", true)
                        .permitAll())

                .httpBasic(Customizer.withDefaults())

                // Ao sair, encerra a sessão e apaga o cookie, para o painel
                // não continuar acessível no mesmo navegador.
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
