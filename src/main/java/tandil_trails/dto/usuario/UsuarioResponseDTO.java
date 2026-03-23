package tandil_trails.dto.usuario;

import java.time.LocalDateTime;

public record UsuarioResponseDTO (
        Long id,
        String username,
        String email,
        String rol,
        LocalDateTime createdAt,
        String avatarUrl
){
}
