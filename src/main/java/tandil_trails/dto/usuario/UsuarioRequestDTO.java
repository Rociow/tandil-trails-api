package tandil_trails.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank @Email
        String email,
        @Size(max = 255)
        String avatarUrl
) {}
