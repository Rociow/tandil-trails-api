package tandil_trails.dto.imagen;

//No pongo el usuario porque va en el token

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ImagenSenderoRequestDTO(
        @NotBlank
        String url,
        @Size (max=500)
        String descripcion
) {
}
