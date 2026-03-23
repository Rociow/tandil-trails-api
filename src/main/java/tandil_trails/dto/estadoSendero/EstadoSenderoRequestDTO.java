package tandil_trails.dto.estadoSendero;

import jakarta.validation.constraints.NotBlank;

public record EstadoSenderoRequestDTO(
        @NotBlank
        String nombre
) {}