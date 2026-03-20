package tandil_trails.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tandil_trails.dto.AuthResponse;
import tandil_trails.dto.LoginRequest;
import tandil_trails.dto.RegisterRequest;
import tandil_trails.service.AuthService;

/*Este controller se encargará de manejar las solicitudes de autenticación, como el registro de nuevos usuarios
y el inicio de sesión. Cuando un usuario se registra o inicia sesión correctamente, el AuthController generará
un token JWT utilizando el JwtService y lo devolverá al cliente para que lo use en futuras solicitudes autenticadas.*/

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
