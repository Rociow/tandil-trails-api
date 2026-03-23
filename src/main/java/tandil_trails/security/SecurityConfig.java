package tandil_trails.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*Esta clase le dice a Spring Security cómo comportarse — qué endpoints son públicos, cuáles requieren autenticación, y registra el filtro JWT.*/

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitamos CSRF porque usamos JWT, no sesiones ni cookies
                .csrf(csrf -> csrf.disable())

                // Definimos qué rutas son públicas y cuáles requieren autenticación
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // registro y login son públicos
                        .requestMatchers(HttpMethod.GET, "/senderos/**").permitAll()
                        .anyRequest().authenticated()            // lo demas requiere JWT
                )

                // Le decimos a Spring que no use sesiones — cada request es independiente
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Registramos nuestro filtro JWT antes del filtro estándar de Spring
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // BCrypt es el algoritmo estándar para hashear passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager es el que Spring usa internamente para autenticar
    // Lo exponemos como bean para poder inyectarlo en el AuthController
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

    /* Request → JwtAuthenticationFilter → (valida JWT) → Controller
                                  ↓ sin JWT
                            /auth/** → pasa igual
                            otro endpoint → 401 Unauthorized
     */