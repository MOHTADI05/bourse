package tn.esprit.mfb.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthentificationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers( // -- Swagger UI v2
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        // -- Swagger UI v3 (OpenAPI)
                        "/v3/api-docs/**",
                        "/swagger-ui/**").permitAll()
                .requestMatchers("/History/**").permitAll()
                .requestMatchers("/Garantor/**").permitAll()
                .requestMatchers("/Fund/**").permitAll()
                .requestMatchers("/DemandeCredit/**").permitAll()
                .requestMatchers("/Credit/**").permitAll()
                .requestMatchers("/Pack/**").permitAll()
                .requestMatchers("/api/bankAccounts/**").permitAll()
                .requestMatchers("/api/transactions/**").permitAll()
                .requestMatchers("/api/Excel/**").permitAll()
                .requestMatchers("/user/auth/**").permitAll()
                .requestMatchers("/api/solva/**").permitAll()
                .requestMatchers("/api/user/**").permitAll()
                .requestMatchers("/immobilier/**").permitAll()
                .requestMatchers("/Account/**").permitAll()
                .requestMatchers("/api/complaints/**").permitAll()
                .requestMatchers("/api/notifications/**").permitAll()
                .requestMatchers("/investisment/**").permitAll()
                .requestMatchers("/inv_history/**").permitAll()
                .requestMatchers("/demande/**").permitAll()
                .requestMatchers("/api/categorieCours/**").permitAll()
                .requestMatchers("/api/cours/**").permitAll()
                .requestMatchers("/api/doc/**").permitAll()
                .requestMatchers("/payments/**").permitAll()
                .requestMatchers("/products/**").permitAll()

                .requestMatchers("/questionRep/**").permitAll()
                .requestMatchers("/quiz/**").permitAll()

                .requestMatchers("/partners/**").permitAll()

                .requestMatchers("/api/tasks/**").permitAll()
                .requestMatchers("/partners/**").permitAll()
                .requestMatchers("/payments").permitAll()
                .requestMatchers("/products/**").permitAll()
                .requestMatchers("/simulation/**").permitAll()
                .requestMatchers("/simu/**").permitAll()
              //  .requestMatchers("/api/admin/**").permitAll()

                .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .logout()
//                .logoutUrl("/user/auth/logout") // Spécifie la route de logout sans le préfixe /user/auth
//                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()) // Gère le succès de la déconnexion avec le code HTTP approprié
//                .deleteCookies("JSESSIONID") // Supprime les cookies si nécessaire
//                .invalidateHttpSession(true);


        return http.build();
    }
    

}

