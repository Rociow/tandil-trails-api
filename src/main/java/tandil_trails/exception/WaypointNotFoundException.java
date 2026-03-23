package tandil_trails.exception;

public class WaypointNotFoundException extends RuntimeException {
    public WaypointNotFoundException(Long id) {
        super("Waypoint no encontrado con id: " + id);
    }
}
