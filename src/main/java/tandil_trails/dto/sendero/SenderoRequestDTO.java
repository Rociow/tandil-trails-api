package tandil_trails.dto.sendero;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tandil_trails.domain.Dificultad;

import java.util.List;

public record SenderoRequestDTO (
        @NotBlank
        String nombre,
        @Size (max = 500)
        String descripcion,
        @NotNull
        Dificultad dificultad,
        @NotNull
        Long estadoId,
        @NotEmpty @NotNull
        List<List<Double>> coordenadas
){
}
