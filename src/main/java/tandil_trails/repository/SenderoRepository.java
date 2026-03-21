package tandil_trails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tandil_trails.domain.Sendero;


//¿Por qué UUID y no Long? Ya lo tenés definido en la entidad, pero vale la pena entenderlo: en una app con datos
// geoespaciales que puede exponer IDs en URLs públicas (rutas en el mapa), UUID evita enumeración trivial de recursos.

public interface SenderoRepository extends JpaRepository<Sendero, Long> {
    @Query(value = "SELECT ST_Length(ruta::geography) FROM senderos WHERE id = :id", nativeQuery = true)
    Double calcularLongitud(@Param("id") Long id);
}
