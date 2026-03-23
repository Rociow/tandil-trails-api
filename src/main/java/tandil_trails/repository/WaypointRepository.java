package tandil_trails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tandil_trails.domain.Waypoint;

import java.util.List;
import java.util.Optional;

public interface WaypointRepository extends JpaRepository<Waypoint, Long> {
    List<Waypoint> findBySenderoId(Long senderoId);

    boolean existsBySenderoIdAndOrden(Long senderoId, int orden);
}
