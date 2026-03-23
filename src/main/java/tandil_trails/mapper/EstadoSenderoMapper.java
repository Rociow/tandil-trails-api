package tandil_trails.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tandil_trails.domain.EstadoSendero;
import tandil_trails.dto.estadoSendero.EstadoSenderoRequestDTO;
import tandil_trails.dto.estadoSendero.EstadoSenderoResponseDTO;

@Mapper(componentModel = "spring")
public interface EstadoSenderoMapper {

    EstadoSenderoResponseDTO toDTO(EstadoSendero estadoSendero);

    @Mapping(target = "id", ignore = true)
    EstadoSendero toEntity(EstadoSenderoRequestDTO dto);
}
