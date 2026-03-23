package tandil_trails.dto.resena;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResenaRequestDTO(
        @NotBlank
        String comentario,
        @NotNull
        @Min(1)
        @Max(5)
        Integer puntuacion
) {
}
