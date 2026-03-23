package tandil_trails.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tandil_trails.domain.ImagenSendero;
import tandil_trails.domain.Sendero;
import tandil_trails.domain.Usuario;
import tandil_trails.dto.imagen.ImagenSenderoRequestDTO;
import tandil_trails.dto.imagen.ImagenSenderoResponseDTO;
import tandil_trails.exception.AccesoNoAutorizadoException;
import tandil_trails.exception.ImagenSenderoNotFoundException;
import tandil_trails.exception.SenderoNotFoundException;
import tandil_trails.exception.UsuarioNotFoundException;
import tandil_trails.mapper.ImagenSenderoMapper;
import tandil_trails.repository.ImagenSenderoRepository;
import tandil_trails.repository.SenderoRepository;
import tandil_trails.repository.UsuarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImagenSenderoService {
    private final ImagenSenderoRepository imagenSenderoRepository;
    private final SenderoRepository senderoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ImagenSenderoMapper imagenSenderoMapper;

    public ImagenSenderoResponseDTO crear(Long senderoId, ImagenSenderoRequestDTO imagenSenderoRequestDTO){
        Sendero sendero = senderoRepository.findById(senderoId)
                .orElseThrow(()->new SenderoNotFoundException(senderoId));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException(username));

        ImagenSendero imagen = imagenSenderoMapper.toEntity(imagenSenderoRequestDTO);
        imagen.setSendero(sendero);
        imagen.setUsuario(usuario);

        return imagenSenderoMapper.toDTO(imagenSenderoRepository.save(imagen));
    }

    public List<ImagenSenderoResponseDTO> listarPorSendero(Long senderoId){
        if (!senderoRepository.existsById(senderoId)) {
            throw new SenderoNotFoundException(senderoId);
        }
        return imagenSenderoRepository.findBySenderoId(senderoId)
                .stream()
                .map(imagenSenderoMapper::toDTO)
                .toList();
    }

    public void eliminar(Long id){
        ImagenSendero imagenSendero = imagenSenderoRepository.findById(id)
                .orElseThrow(() -> new ImagenSenderoNotFoundException(id));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!imagenSendero.getUsuario().getUsername().equals(username)) {
            throw new AccesoNoAutorizadoException();
        }

        imagenSenderoRepository.delete(imagenSendero);
    }
}
