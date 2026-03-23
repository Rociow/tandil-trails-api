package tandil_trails.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tandil_trails.dto.auth.AuthResponse;
import tandil_trails.dto.auth.LoginRequest;
import tandil_trails.dto.auth.RegisterRequest;
import tandil_trails.service.AuthService;

/*Este controller se encargará de manejar las solicitudes de autenticación, como el registro de nuevos usuarios
y el inicio de sesión. Cuando un usuario se registra o inicia sesión correctamente, el AuthController generará
un token JWT utilizando el JwtService y lo devolverá al cliente para que lo use en futuras solicitudes autenticadas.*/

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
