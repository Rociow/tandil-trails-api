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
    @Value("${gemini.api-key}")
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
    public SearchFilterDTO extractFilters(String userQuery) {
        String prompt = buildPrompt(userQuery);

        // 1. Armamos el body que espera Gemini
        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        try {
            // 2. Hacemos la llamada POST a la API
            Map<String, Object> response = restClient.post()
                    .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey) // a dónde llamamos
                    .contentType(MediaType.APPLICATION_JSON) // le avisamos que mandamos json
                    .body(body)// le mandamos el body que armamos
                    .retrieve()// hacemos la llamada
                    .body(new ParameterizedTypeReference<>() { // le decimos que esperamos un Map<String, Object> como respuesta
                    });

            // 3. Navegamos la respuesta hasta el texto
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            String jsonText = (String) parts.get(0).get("text");

            // 4. Parseamos el JSON a nuestro DTO
            return objectMapper.readValue(jsonText, SearchFilterDTO.class);

        } catch (Exception e) {
            // 5. Si algo falla, devolvemos filtros vacíos
            return new SearchFilterDTO(null, null, null, null, List.of());
        }
    }
}
