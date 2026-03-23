package tandil_trails.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tandil_trails.domain.ImagenSendero;
import tandil_trails.dto.imagen.ImagenSenderoResponseDTO;
import tandil_trails.dto.imagen.ImagenSenderoRequestDTO;

@Mapper(componentModel = "spring")
public interface ImagenSenderoMapper {

    @Mapping(target = "usuarioUsername", source = "usuario.username")
    ImagenSenderoResponseDTO toDTO(ImagenSendero imagen);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sendero", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    ImagenSendero toEntity(ImagenSenderoRequestDTO imagenSenderoRequestDTO);
}
