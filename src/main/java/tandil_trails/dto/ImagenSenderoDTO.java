package tandil_trails.dto;

import java.time.LocalDateTime;

public record ImagenSenderoDTO(
        Long id,
        String url,
        String descripcion,
        LocalDateTime fechaCreacion,
        String usuarioUsername
) {}
