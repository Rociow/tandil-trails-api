package tandil_trails.exception;

public class SenderoNotFoundException extends RuntimeException {
    public SenderoNotFoundException(Long id) {
        super("Sendero no encontrado con id: " + id);
    }
}