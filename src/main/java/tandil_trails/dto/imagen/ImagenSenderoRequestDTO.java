package tandil_trails.dto.imagen;

//No pongo el usuario porque va en el token

public record ImagenSenderoRequestDTO(
        String url,
        String descripcion
) {
}
