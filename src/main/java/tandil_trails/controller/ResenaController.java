package tandil_trails.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tandil_trails.dto.resena.ResenaRequestDTO;
import tandil_trails.dto.resena.ResenaResponseDTO;
import tandil_trails.service.ResenaService;

import java.util.List;

@RestController
@RequestMapping("/senderos/{senderoId}/resenas")
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaService resenaService;

    @GetMapping
    public ResponseEntity<List<ResenaResponseDTO>> listarPorSendero(@PathVariable Long senderoId) {
        return ResponseEntity.ok(resenaService.listarPorSendero(senderoId));
    }

    @PostMapping
    public ResponseEntity<ResenaResponseDTO> crear(@PathVariable Long senderoId,
                                                   @RequestBody ResenaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resenaService.crear(senderoId, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResenaResponseDTO> actualizar(@PathVariable Long id,
                                                        @RequestBody ResenaRequestDTO dto) {
        return ResponseEntity.ok(resenaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        resenaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}