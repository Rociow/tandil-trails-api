package tandil_trails.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tandil_trails.domain.Usuario;
import tandil_trails.repository.UsuarioRepository;

/**
 * Esta clase implementa UserDetailsService para cargar los detalles del usuario desde la base de datos.
 * Utiliza el UsuarioRepository para buscar el usuario por su nombre de usuario.
 * Proporciona los detalles necesarios del usuario para la autenticación y la autorización.
 * Su único trabajo es ir al repositorio, buscar al usuario y entregárselo a Spring Security para que pueda manejar la autenticación y autorización de ese usuario.
 * UserDetailsService (Service): Es el "¿Cómo lo encuentro?" (la lógica para recuperarlo de la persistencia).
 */

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario loadUserByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}