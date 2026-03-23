package tandil_trails.exception;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(String username) {
        super("El usuario con el username" + username + "no existe.");
    }
}
