package tandil_trails.mapper;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.PrecisionModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import tandil_trails.domain.Resena;
import tandil_trails.domain.Sendero;
import tandil_trails.dto.sendero.SenderoDetalleDTO;
import tandil_trails.dto.sendero.SenderoRequestDTO;
import tandil_trails.dto.sendero.SenderoResumenDTO;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring", uses = {WaypointMapper.class, ImagenSenderoMapper.class})
public interface SenderoMapper {

    @Mapping(target = "estado", source = "estado.nombre")
    @Mapping(target = "coordenadas", source = "ruta", qualifiedByName = "lineStringToCoordenadas")
    @Mapping(target = "promedioResenas", source = "resenas", qualifiedByName = "calcularPromedio")
    @Mapping(target = "cantidadResenas", source = "resenas", qualifiedByName = "calcularCantidad")
    SenderoDetalleDTO toDetalleDTO(Sendero sendero);

    @Mapping(target = "estado", source = "estado.nombre")
    SenderoResumenDTO toResumenDTO(Sendero sendero);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "longitud", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "waypoints", ignore = true)
    @Mapping(target = "resenas", ignore = true)
    @Mapping(target = "imagenes", ignore = true)
    @Mapping(target = "ruta", source = "coordenadas", qualifiedByName = "coordenadasToLineString")
    Sendero toEntity(SenderoRequestDTO dto);

    //métodos default dentro de la interfaz — MapStruct los llama cuando ve el qualifiedByName.
    // El c.x es longitud y c.y es latitud, consistente con el formato GeoJSON [lng, lat].
    @Named("lineStringToCoordenadas")
    default List<List<Double>> lineStringToCoordenadas(LineString lineString) {
        if (lineString == null) return List.of();
        return Arrays.stream(lineString.getCoordinates())
                .map(c -> List.of(c.x, c.y))
                .toList();
    }

    @Named("coordenadasToLineString")
    default LineString coordenadasToLineString(List<List<Double>> coordenadas) {
        GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate[] coords = coordenadas.stream()
                .map(c -> new Coordinate(c.get(0), c.get(1)))
                .toArray(Coordinate[]::new);
        return gf.createLineString(coords);
    }

    @Named("calcularPromedio")
    default double calcularPromedio(List<Resena> resenas) {
        if (resenas == null || resenas.isEmpty()) return 0.0;
        return resenas.stream()
                .mapToInt(Resena::getPuntuacion)
                .average()
                .orElse(0.0);
    }

    @Named("calcularCantidad")
    default int calcularCantidad(List<Resena> resenas) {
        if (resenas == null) return 0;
        return resenas.size();
    }
}