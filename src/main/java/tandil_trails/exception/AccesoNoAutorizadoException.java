package tandil_trails.exception;

public class AccesoNoAutorizadoException extends RuntimeException {
    public AccesoNoAutorizadoException() {
        super("No tenés permiso para realizar esta acción");
    }
}