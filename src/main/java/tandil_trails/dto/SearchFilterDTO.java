package tandil_trails.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tandil_trails.domain.Dificultad;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) // Esto es para que si la IA devuelve campos extra que no tenemos en el DTO, no falle el parseo.
public record SearchFilterDTO (
         Dificultad dificultad,
         Double longitudMin,
         Double longitudMax,
         Double ratingMinimo,
         List<String> keywords
        ) {
}
