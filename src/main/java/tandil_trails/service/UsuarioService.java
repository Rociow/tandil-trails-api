package tandil_trails.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tandil_trails.domain.Usuario;
import tandil_trails.dto.usuario.UsuarioRequestDTO;
import tandil_trails.dto.usuario.UsuarioResponseDTO;
import tandil_trails.exception.UsuarioNotFoundException;
import tandil_trails.mapper.UsuarioMapper;
import tandil_trails.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioResponseDTO obtenerPerfil() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException(username));
        return usuarioMapper.toDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO actualizar(UsuarioRequestDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException(username));

        usuario.setEmail(dto.email());
        usuario.setAvatarUrl(dto.avatarUrl());

        return usuarioMapper.toDTO(usuarioRepository.save(usuario));
    }

    @Transactional
    public void eliminar() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException(username));
        usuarioRepository.delete(usuario);
    }
}