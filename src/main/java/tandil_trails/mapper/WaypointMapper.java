package tandil_trails.mapper;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import tandil_trails.domain.Waypoint;
import tandil_trails.dto.WaypointRequestDTO;
import tandil_trails.dto.WaypointResponseDTO;

@Mapper(componentModel = "spring")
public interface WaypointMapper {

    @Mapping(target = "latitud", expression = "java(waypoint.getUbicacion().getY())")
    @Mapping(target = "longitud", expression = "java(waypoint.getUbicacion().getX())")
    WaypointResponseDTO toDTO(Waypoint waypoint);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sendero", ignore = true)
    @Mapping(target = "ubicacion", source = ".", qualifiedByName = "crearPoint")
    Waypoint toEntity(WaypointRequestDTO dto);

    @Named("crearPoint")
    default Point crearPoint(WaypointRequestDTO dto) {
        GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4326);
        return gf.createPoint(new Coordinate(dto.longitud(), dto.latitud()));
    }
}

