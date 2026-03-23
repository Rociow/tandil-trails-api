package tandil_trails.dto.waypoint;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record WaypointRequestDTO(
        @NotBlank
        String nombre,
        @Size(max = 500)
        String descripcion,
        @NotNull
        Integer orden,
        double latitud,
        double longitud
) {}
