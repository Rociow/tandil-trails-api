package tandil_trails.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tandil_trails.domain.Waypoint;
import tandil_trails.dto.WaypointDTO;

@Mapper(componentModel = "spring")
public interface WaypointMapper {

    @Mapping(target = "latitud", expression = "java(waypoint.getUbicacion().getY())")
    @Mapping(target = "longitud", expression = "java(waypoint.getUbicacion().getX())")
    WaypointDTO toDTO(Waypoint waypoint);
}
