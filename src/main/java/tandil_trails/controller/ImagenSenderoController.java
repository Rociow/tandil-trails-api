package tandil_trails.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tandil_trails.dto.imagen.ImagenSenderoRequestDTO;
import tandil_trails.dto.imagen.ImagenSenderoResponseDTO;
import tandil_trails.service.ImagenSenderoService;

import java.util.List;

@RestController
@RequestMapping("/senderos/{senderoId}/imagenes")
@RequiredArgsConstructor
public class ImagenSenderoController {

    private final ImagenSenderoService imagenSenderoService;

    @GetMapping
    public ResponseEntity<List<ImagenSenderoResponseDTO>> listarPorSendero(@PathVariable Long senderoId) {
        return ResponseEntity.ok(imagenSenderoService.listarPorSendero(senderoId));
    }

    @PostMapping
    public ResponseEntity<ImagenSenderoResponseDTO> crear(@PathVariable Long senderoId,
                                                          @RequestBody ImagenSenderoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(imagenSenderoService.crear(senderoId, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        imagenSenderoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
