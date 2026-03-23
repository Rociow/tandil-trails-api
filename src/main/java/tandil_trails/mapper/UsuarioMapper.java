package tandil_trails.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tandil_trails.domain.Usuario;
import tandil_trails.dto.usuario.UsuarioRequestDTO;
import tandil_trails.dto.usuario.UsuarioResponseDTO;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioResponseDTO toDTO(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    Usuario toEntity(UsuarioRequestDTO dto);
}
