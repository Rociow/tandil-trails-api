package tandil_trails.dto.sendero;

import tandil_trails.domain.Dificultad;

import java.util.List;

public record SenderoRequestDTO (
        String nombre,
        String descripcion,
        Dificultad dificultad,
        Long estadoId,
        List<List<Double>> coordenadas
){
}
