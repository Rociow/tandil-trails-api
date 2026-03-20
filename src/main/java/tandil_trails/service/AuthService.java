package tandil_trails.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tandil_trails.domain.Usuario;
import tandil_trails.dto.AuthResponse;
import tandil_trails.dto.LoginRequest;
import tandil_trails.dto.RegisterRequest;
import tandil_trails.repository.UsuarioRepository;
import tandil_trails.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest registerRequest){
        Usuario usuario = new Usuario();
        usuario.setUsername(registerRequest.username());
        usuario.setPassword(passwordEncoder.encode(registerRequest.password()));
        usuario.setRol("ROLE_USER");
        usuario.setEmail(registerRequest.email());
        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(usuario);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(
            new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                loginRequest.username(),
                loginRequest.password()
            )
        );

        Usuario usuario = usuarioRepository.findByUsername(loginRequest.username())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.generateToken(usuario);
        return new AuthResponse(token);
    }
}
