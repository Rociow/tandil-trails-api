package tandil_trails.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tandil_trails.dto.sendero.SenderoDetalleDTO;
import tandil_trails.dto.sendero.SenderoRequestDTO;
import tandil_trails.dto.sendero.SenderoResumenDTO;
import tandil_trails.service.SenderoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/senderos")
@RequiredArgsConstructor
public class SenderoController {
    private final SenderoService senderoService;

    @GetMapping
    public ResponseEntity<Page<SenderoResumenDTO>> listarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(senderoService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SenderoDetalleDTO> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(senderoService.obtenerPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<SenderoResumenDTO>> obtenerPorNombre(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(senderoService.obtenerPorNombre(pageable, q));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SenderoDetalleDTO> crear(@RequestBody @Valid SenderoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(senderoService.crear(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SenderoDetalleDTO> actualizar(@PathVariable Long id, @RequestBody @Valid SenderoRequestDTO dto) {
        return ResponseEntity.ok(senderoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        senderoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar-ia")
    public ResponseEntity<?> buscarConIA(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (query.isBlank()) {
            return ResponseEntity.badRequest().body("Escribí qué tipo de sendero estás buscando");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<SenderoResumenDTO> resultados = senderoService.busquedaAvanzada(pageable, query);

        if (resultados.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                    "mensaje", "No encontramos senderos que coincidan con tu búsqueda",
                    "resultados", List.of()
            ));
        }
        return ResponseEntity.ok(resultados);
    }

}
