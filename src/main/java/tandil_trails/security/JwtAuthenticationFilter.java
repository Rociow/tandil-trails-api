package tandil_trails.security;

/*clase que se ejecuta antes de que el request llegue al controller, se encarga de validar el token y
setear el contexto de seguridad para que el controller pueda acceder a la informacion del usuario autenticado*/

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Leer el header Authorization del request
        String authHeader = request.getHeader("Authorization");

        // 2. Si no hay header o no empieza con "Bearer ", dejamos pasar sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extraer el token — sacamos los primeros 7 caracteres ("Bearer ")
        String token = authHeader.substring(7);

        // 4. Extraer el username del token usando JwtService
        String username = jwtService.extractUsername(token);

        // 5. Si hay username y el usuario no está ya autenticado en este request
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 6. Cargar el usuario completo desde la base
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 7. Validar que el token sea válido para ese usuario
            if (jwtService.isTokenValid(token, userDetails)) {

                // 8. Crear el objeto de autenticación que Spring Security entiende
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null, // credentials — null porque ya validamos con JWT
                                userDetails.getAuthorities() // roles del usuario
                        );

                // 9. Agregar info del request al contexto de autenticación
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 10. Registrar la autenticación en Spring Security para este request
                SecurityContextHolder.getContext().setAuthentication(authToken);
                // Spring Security guarda quién está autenticado en el request actual. Una vez que seteás eso, todos los controllers y servicios pueden saber quién es el usuario sin que vos se lo pases explícitamente.
            }
        }

        // 11. Continuar con el siguiente filtro de la cadena
        filterChain.doFilter(request, response);
    }
}

