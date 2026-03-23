package tandil_trails.dto;

public record WaypointRequestDTO(
        String nombre,
        String descripcion,
        int orden,
        double latitud,
        double longitud
) {}
