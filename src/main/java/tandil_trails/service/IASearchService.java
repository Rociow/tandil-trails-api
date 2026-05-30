package tandil_trails.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tandil_trails.dto.SearchFilterDTO;

import java.util.List;
import java.util.Map;

// Este servicio se encargaría de recibir los parámetros de búsqueda, construir la consulta adecuada para la IA y procesar los resultados.
@Service
public class IASearchService {
    @Value("${groq.api-key}")
    private String apiKey;
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    private String buildPrompt(String userQuery) {
        return """
        Sos un asistente de una app de senderismo en Tandil, Argentina.
        Tu única tarea es analizar la consulta del usuario y devolver un JSON con filtros de búsqueda.
                        
        Campos del JSON:
        - "dificultad": "FACIL", "MODERADO" o "DIFICIL". Null si no se menciona.
        - "longitudMinima": número en metros. Null si no se menciona.
        - "longitudMaxima": número en metros. Null si no se menciona.
        - "ratingMinimo": número entre 1.0 y 5.0. Null si no se menciona.
        - "keywords": lista de strings con palabras clave para buscar en nombre y descripción. Lista vacía si no hay.
                        
        Reglas estrictas:
        - Convertí cualquier unidad de distancia a metros.
        - "corto" o "tranquilo" sugiere longitudMaxima de 3000 y dificultad FACIL.
        - "largo" o "desafiante" sugiere longitudMinima de 8000.
        - Si no podés inferir un filtro con certeza, dejalo null.
        - Devolvé ÚNICAMENTE el JSON, sin explicaciones, sin markdown, sin bloques de código.
                        
        Consulta del usuario: "%s"
        """.formatted(userQuery);
    }

    public IASearchService(ObjectMapper objectMapper) {
        this.restClient = RestClient.create();
        this.objectMapper = objectMapper;
    }

    // Aquí se implementaría la lógica para enviar la consulta a la IA y recibir los filtros estructurados.
    @SuppressWarnings("unchecked")
    public SearchFilterDTO extractFilters(String userQuery) {
        Map<String, Object> body = Map.of(
                "model", "llama-3.1-8b-instant",
                "messages", List.of(
                        Map.of("role", "user", "content", buildPrompt(userQuery))
                )
        );

        try {
            Map<String, Object> response = restClient.post()
                    .uri("https://api.groq.com/openai/v1/chat/completions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + apiKey)
                    .body(body)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            String jsonText = (String) message.get("content");

            SearchFilterDTO filtros = objectMapper.readValue(jsonText, SearchFilterDTO.class);

            return filtros;

        } catch (Exception e) {
            e.printStackTrace();
            return new SearchFilterDTO(null, null, null, null, List.of());
        }
    }

}
