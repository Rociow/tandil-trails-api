package tandil_trails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tandil_trails.domain.EstadoSendero;

public interface EstadoSenderoRepository extends JpaRepository<EstadoSendero, Long> {
}
