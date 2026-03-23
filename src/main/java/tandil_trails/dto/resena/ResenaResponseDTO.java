package tandil_trails.dto.resena;


import java.time.LocalDateTime;

public record ResenaResponseDTO(
        Long id,
        String comentario,
        Integer puntuacion,
        LocalDateTime fechaCreacion,
        String username
) {
}
