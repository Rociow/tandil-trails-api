package tandil_trails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tandil_trails.domain.Resena;

import java.util.List;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findBySenderoId(Long senderoId);
    boolean existsBySenderoIdAndUsuarioId(Long senderoId, Long usuarioId);
}