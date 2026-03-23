package tandil_trails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tandil_trails.domain.ImagenSendero;

import java.util.List;

public interface ImagenSenderoRepository extends JpaRepository<ImagenSendero, Long> {
    List<ImagenSendero> findBySenderoId(Long senderoId);
}
