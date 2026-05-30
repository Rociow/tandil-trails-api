package tandil_trails.specification;

import org.springframework.data.jpa.domain.Specification;
import tandil_trails.domain.Dificultad;
import tandil_trails.domain.Sendero;

import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class SenderoSpecification {

    public static Specification<Sendero> conDificultad(Dificultad dificultad) {
        if (dificultad == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("dificultad"), dificultad);
    }

    public static Specification<Sendero> conLongitudMinima(Double min) {
        if (min == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("longitud"), min);
    }

    public static Specification<Sendero> conLongitudMaxima(Double max) {
        if (max == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("longitud"), max);
    }

    public static Specification<Sendero> conKeywords(List<String> keywords) {
        if (keywords == null || keywords.isEmpty()) return null;
        return (root, query, cb) -> {
            Predicate[] predicados = keywords.stream()
                    .map(keyword -> {
                        String patron = "%" + keyword.toLowerCase() + "%";
                        Predicate enNombre = (Predicate) cb.like(cb.lower(root.get("nombre")), patron); //instrucción de buscar la keyword en nombre, ignorando mayúsculas
                        Predicate enDescripcion = cb.like(cb.lower(root.get("descripcion")), patron);
                        return cb.or(enNombre, enDescripcion); //si la keyword aparece en nombre o descripción, el sendero es candidato a ser incluido en los resultados.
                    })
                    .toArray(Predicate[]::new);

            return cb.and(predicados); //unimos todas las keywords con AND, es decir, el sendero debe contener todas las keywords para ser incluido en los resultados.
        };
    }


    public static Specification<Sendero> soloHabilitados() {
        return (root, query, cb) -> cb.equal(root.join("estado").get("nombre"), "Habilitado");
    }
}
