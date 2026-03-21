package tandil_trails.exception;

public class EstadoSenderoNotFoundException extends RuntimeException {
    public EstadoSenderoNotFoundException(Long id) {
        super("Estado de sendero no encontrado con id: " + id);
    }
}
