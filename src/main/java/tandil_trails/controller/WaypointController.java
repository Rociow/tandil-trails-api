package tandil_trails.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tandil_trails.dto.WaypointRequestDTO;
import tandil_trails.dto.WaypointResponseDTO;
import tandil_trails.service.WaypointService;

import java.util.List;

@RestController
@RequestMapping("/senderos/{senderoId}/waypoints")
@RequiredArgsConstructor
public class WaypointController {

    private final WaypointService waypointService;

    @GetMapping
    public ResponseEntity<List<WaypointResponseDTO>> listarPorSendero(@PathVariable Long senderoId) {
        return ResponseEntity.ok(waypointService.listarPorSendero(senderoId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<WaypointResponseDTO> crear(@PathVariable Long senderoId,
                                                     @RequestBody WaypointRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(waypointService.crear(senderoId, dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<WaypointResponseDTO> actualizar(@PathVariable Long id,
                                                          @RequestBody WaypointRequestDTO dto) {
        return ResponseEntity.ok(waypointService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        waypointService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
