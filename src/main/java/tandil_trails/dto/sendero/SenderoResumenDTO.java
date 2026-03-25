package tandil_trails.dto.sendero;

//cuando el usuario abre el mapa y carga la lista de senderos. No necesitás la ruta
// completa ni los waypoints, solo los datos para mostrar una card o un marcador. Menos datos = respuesta más liviana.

import tandil_trails.domain.Dificultad;
import tandil_trails.dto.estadoSendero.EstadoSenderoResponseDTO;

public record SenderoResumenDTO(
        Long id,
        String nombre,
        String descripcion,
        double longitud,
        Dificultad dificultad,
        EstadoSenderoResponseDTO estado
) {}
