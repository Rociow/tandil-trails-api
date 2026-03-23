package tandil_trails.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tandil_trails.domain.Resena;
import tandil_trails.dto.resena.ResenaRequestDTO;
import tandil_trails.dto.resena.ResenaResponseDTO;

@Mapper(componentModel = "spring")
public interface ResenaMapper {

    @Mapping(target = "username", source = "usuario.username")
    ResenaResponseDTO toDTO(Resena resena);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sendero", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    Resena toEntity(ResenaRequestDTO dto);
}