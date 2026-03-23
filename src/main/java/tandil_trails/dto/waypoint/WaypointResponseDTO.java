package tandil_trails.dto.waypoint;

public record WaypointResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        int orden,
        double latitud,
        double longitud
) {}
