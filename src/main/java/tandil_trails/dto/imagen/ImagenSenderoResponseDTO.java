package tandil_trails.dto.imagen;

import java.time.LocalDateTime;

public record ImagenSenderoResponseDTO(
        Long id,
        String url,
        String descripcion,
        LocalDateTime fechaCreacion,
        String usuarioUsername
) {}
