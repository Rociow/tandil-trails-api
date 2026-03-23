package tandil_trails.exception;

public class WaypointOrdenDuplicadoException extends RuntimeException {
    public WaypointOrdenDuplicadoException(Long senderoId, int orden) {
        super("Ya existe un waypoint con orden " + orden + " en el sendero con id: " + senderoId);
    }
}
