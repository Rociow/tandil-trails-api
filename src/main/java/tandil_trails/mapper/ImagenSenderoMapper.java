package tandil_trails.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tandil_trails.domain.ImagenSendero;
import tandil_trails.dto.ImagenSenderoDTO;

@Mapper(componentModel = "spring")
public interface ImagenSenderoMapper {

    @Mapping(target = "usuarioUsername", source = "usuario.username")
    ImagenSenderoDTO toDTO(ImagenSendero imagen);
}
