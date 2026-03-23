package tandil_trails.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tandil_trails.dto.estadoSendero.EstadoSenderoRequestDTO;
import tandil_trails.dto.estadoSendero.EstadoSenderoResponseDTO;
import tandil_trails.service.EstadoSenderoService;

import java.util.List;

@RestController
@RequestMapping("/estados-sendero")
@RequiredArgsConstructor
public class EstadoSenderoController {

    private final EstadoSenderoService estadoSenderoService;

    @GetMapping
    public ResponseEntity<List<EstadoSenderoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(estadoSenderoService.listarTodos());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EstadoSenderoResponseDTO> crear(@RequestBody EstadoSenderoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoSenderoService.crear(dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        estadoSenderoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
