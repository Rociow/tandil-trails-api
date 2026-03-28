package tandil_trails.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tandil_trails.domain.Sendero;
import tandil_trails.domain.Usuario;
import tandil_trails.dto.usuario.UsuarioRequestDTO;
import tandil_trails.dto.usuario.UsuarioResponseDTO;
import tandil_trails.exception.UsuarioNotFoundException;
import tandil_trails.mapper.UsuarioMapper;
import tandil_trails.repository.SenderoRepository;
import tandil_trails.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SenderoRepository senderoRepository;
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

    @Transactional
    public void agregarFavorito(Long senderoId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException(username));
        Sendero sendero = senderoRepository.findById(senderoId)
                .orElseThrow(() -> new RuntimeException("Sendero no encontrado"));
        if (!usuario.getFavoritos().contains(sendero)) {
            usuario.getFavoritos().add(sendero);
            usuarioRepository.save(usuario);
        }
    }

    @Transactional
    public void eliminarFavorito(Long senderoId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException(username));
        Sendero sendero = senderoRepository.findById(senderoId)
                .orElseThrow(() -> new RuntimeException("Sendero no encontrado"));
        usuario.getFavoritos().remove(sendero);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void agregarVisitado(Long senderoId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException(username));
        Sendero sendero = senderoRepository.findById(senderoId)
                .orElseThrow(() -> new RuntimeException("Sendero no encontrado"));
        if (!usuario.getVisitados().contains(sendero)) {
            usuario.getVisitados().add(sendero);
            usuarioRepository.save(usuario);
        }
    }

    @Transactional
    public void eliminarVisitado(Long senderoId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException(username));
        Sendero sendero = senderoRepository.findById(senderoId)
                .orElseThrow(() -> new RuntimeException("Sendero no encontrado"));
        usuario.getVisitados().remove(sendero);
        usuarioRepository.save(usuario);
    }
}