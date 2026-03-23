package tandil_trails.exception;

public class ResenaYaExisteException extends RuntimeException {
    public ResenaYaExisteException(Long id) {
        super("Ya se creó una reseña para este sendero");
    }
}
