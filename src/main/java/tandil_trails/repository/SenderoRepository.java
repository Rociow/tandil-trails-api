package tandil_trails.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tandil_trails.domain.Sendero;
import tandil_trails.dto.sendero.SenderoResumenDTO;

import java.util.List;
import java.util.Optional;


//¿Por qué UUID y no Long? Ya lo tenés definido en la entidad, pero vale la pena entenderlo: en una app con datos
// geoespaciales que puede exponer IDs en URLs públicas (rutas en el mapa), UUID evita enumeración trivial de recursos.

public interface SenderoRepository extends JpaRepository<Sendero, Long>, JpaSpecificationExecutor<Sendero> {
    @Query(value = "SELECT ST_Length(ruta::geography) FROM senderos WHERE id = :id", nativeQuery = true)
    Double calcularLongitud(@Param("id") Long id);

    Page<Sendero> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
