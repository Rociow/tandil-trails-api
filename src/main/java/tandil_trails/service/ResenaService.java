package tandil_trails.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tandil_trails.domain.Resena;
import tandil_trails.domain.Sendero;
import tandil_trails.domain.Usuario;
import tandil_trails.dto.resena.ResenaRequestDTO;
import tandil_trails.dto.resena.ResenaResponseDTO;
import tandil_trails.exception.*;
import tandil_trails.mapper.ResenaMapper;
import tandil_trails.repository.ResenaRepository;
import tandil_trails.repository.SenderoRepository;
import tandil_trails.repository.UsuarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResenaService {

    private final ResenaRepository resenaRepository;
    private final SenderoRepository senderoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ResenaMapper resenaMapper;

    @Transactional
    public ResenaResponseDTO crear(Long senderoId, ResenaRequestDTO dto) {
        Sendero sendero = senderoRepository.findById(senderoId)
                .orElseThrow(() -> new SenderoNotFoundException(senderoId));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException(username));

        if (resenaRepository.existsBySenderoIdAndUsuarioId(senderoId, usuario.getId())) {
            throw new ResenaYaExisteException(senderoId);
        }

        Resena resena = resenaMapper.toEntity(dto);
        resena.setSendero(sendero);
        resena.setUsuario(usuario);

        return resenaMapper.toDTO(resenaRepository.save(resena));
    }

    public List<ResenaResponseDTO> listarPorSendero(Long senderoId) {
        if (!senderoRepository.existsById(senderoId)) {
            throw new SenderoNotFoundException(senderoId);
        }
        return resenaRepository.findBySenderoId(senderoId)
                .stream()
                .map(resenaMapper::toDTO)
                .toList();
    }

    @Transactional
    public ResenaResponseDTO actualizar(Long id, ResenaRequestDTO dto) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResenaNotFoundException(id));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Username del token: " + username);
        System.out.println("Username de la reseña: " + resena.getUsuario().getUsername());
        if (!resena.getUsuario().getUsername().equals(username)) {
            throw new AccesoNoAutorizadoException();
        }

        resena.setComentario(dto.comentario());
        resena.setPuntuacion(dto.puntuacion());

        return resenaMapper.toDTO(resenaRepository.save(resena));
    }

    @Transactional
    public void eliminar(Long id) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResenaNotFoundException(id));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!resena.getUsuario().getUsername().equals(username)) {
            throw new AccesoNoAutorizadoException();
        }

        resenaRepository.delete(resena);
    }
}
