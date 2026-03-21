package tandil_trails.dto;

public record WaypointDTO(
        Long id,
        String nombre,
        String descripcion,
        int orden,
        double latitud,
        double longitud
) {}
