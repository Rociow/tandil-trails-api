package tandil_trails.dto.waypoint;

public record WaypointRequestDTO(
        String nombre,
        String descripcion,
        int orden,
        double latitud,
        double longitud
) {}
