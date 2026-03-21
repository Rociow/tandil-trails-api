package tandil_trails.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tandil_trails.dto.SenderoDetalleDTO;
import tandil_trails.dto.SenderoRequestDTO;
import tandil_trails.dto.SenderoResumenDTO;
import tandil_trails.service.SenderoService;

import java.util.List;

@RestController
@RequestMapping("/senderos")
@RequiredArgsConstructor
public class SenderoController {
    private final SenderoService senderoService;

    @GetMapping
    public ResponseEntity<List<SenderoResumenDTO>> listarTodos(){
        return ResponseEntity.ok(senderoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SenderoDetalleDTO> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(senderoService.obtenerPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SenderoDetalleDTO> crear(@RequestBody SenderoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(senderoService.crear(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SenderoDetalleDTO> actualizar(@PathVariable Long id, @RequestBody SenderoRequestDTO dto) {
        return ResponseEntity.ok(senderoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        senderoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
