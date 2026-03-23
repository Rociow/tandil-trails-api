package tandil_trails.exception;

public class ImagenSenderoNotFoundException extends RuntimeException {
    public ImagenSenderoNotFoundException(Long id) {
        super("Imagen no encontrada con id: " + id);
    }
}