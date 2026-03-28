package tandil_trails.dto.usuario;

import tandil_trails.dto.sendero.SenderoResumenDTO;

import java.time.LocalDateTime;
import java.util.List;

public record UsuarioResponseDTO (
        Long id,
        String username,
        String email,
        String rol,
        LocalDateTime createdAt,
        String avatarUrl,
        List<SenderoResumenDTO> favoritos,
        List<SenderoResumenDTO> visitados
){
}
