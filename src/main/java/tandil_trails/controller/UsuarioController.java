package tandil_trails.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tandil_trails.dto.usuario.UsuarioRequestDTO;
import tandil_trails.dto.usuario.UsuarioResponseDTO;
import tandil_trails.service.UsuarioService;

@RestController
@RequestMapping("/usuarios/me")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<UsuarioResponseDTO> obtenerPerfil(){
        return ResponseEntity.ok(usuarioService.obtenerPerfil());
    }

    @PutMapping
    public ResponseEntity<UsuarioResponseDTO> actualizar(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO){
        return ResponseEntity.ok(usuarioService.actualizar(usuarioRequestDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminar(){
        usuarioService.eliminar();
        return ResponseEntity.noContent().build();
    }
}
