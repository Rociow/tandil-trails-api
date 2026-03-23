package tandil_trails.dto.sendero;

import tandil_trails.domain.Dificultad;
import tandil_trails.dto.ImagenSenderoDTO;
import tandil_trails.dto.waypoint.WaypointResponseDTO;

import java.util.List;

//cuando el usuario toca un
// sendero específico y quiere ver toda la información: la ruta dibujada en el mapa, los puntos de interés, las fotos.

public record SenderoDetalleDTO(
        Long id,
        String nombre,
        String descripcion,
        double longitud,
        Dificultad dificultad,
        String estado,
        List<List<Double>> coordenadas,
        List<WaypointResponseDTO> waypoints,
        double promedioResenas,
        int cantidadResenas,
        List<ImagenSenderoDTO> imagenes
) {}