package tandil_trails.exception;

public class ResenaNotFoundException extends RuntimeException {
    public ResenaNotFoundException(Long id) {
        super("Reseña no encontrada con id: " + id);
    }
}
