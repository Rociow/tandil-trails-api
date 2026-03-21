package tandil_trails.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tandil_trails.domain.EstadoSendero;
import tandil_trails.domain.Sendero;
import tandil_trails.dto.SenderoDetalleDTO;
import tandil_trails.dto.SenderoRequestDTO;
import tandil_trails.dto.SenderoResumenDTO;
import tandil_trails.exception.EstadoSenderoNotFoundException;
import tandil_trails.exception.SenderoNotFoundException;
import tandil_trails.mapper.SenderoMapper;
import tandil_trails.repository.EstadoSenderoRepository;
import tandil_trails.repository.SenderoRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SenderoService {
    private final SenderoRepository senderoRepository;
    private final EstadoSenderoRepository estadoSenderoRepository;
    private final SenderoMapper senderoMapper;

    @Transactional
    public SenderoDetalleDTO crear(SenderoRequestDTO senderoRequestDTO){
        Sendero sendero = senderoMapper.toEntity(senderoRequestDTO);
        EstadoSendero estadoSendero = estadoSenderoRepository.findById(senderoRequestDTO.estadoId())
                .orElseThrow(() -> new EstadoSenderoNotFoundException(senderoRequestDTO.estadoId()));
        sendero.setEstado(estadoSendero);

        Sendero senderoGuardado = senderoRepository.save(sendero);

        Double longitud = senderoRepository.calcularLongitud(senderoGuardado.getId());
        senderoGuardado.setLongitud(longitud);
        senderoRepository.save(senderoGuardado);

        return senderoMapper.toDetalleDTO(senderoGuardado);
    }

    public SenderoDetalleDTO obtenerPorId(Long id) {
        Sendero sendero = senderoRepository.findById(id)
                .orElseThrow(() -> new SenderoNotFoundException(id));
        return senderoMapper.toDetalleDTO(sendero);
    }

    public List<SenderoResumenDTO> listarTodos(){
        List<Sendero> senderos = senderoRepository.findAll();
        return senderos.stream()
                .map(senderoMapper::toResumenDTO)
                .toList();
    }

    public SenderoDetalleDTO actualizar(Long id, SenderoRequestDTO senderoRequestDTO) {
        Sendero sendero = senderoRepository.findById(id)
                .orElseThrow(() -> new SenderoNotFoundException(id));

        EstadoSendero estado = estadoSenderoRepository.findById(senderoRequestDTO.estadoId())
                .orElseThrow(() -> new EstadoSenderoNotFoundException(senderoRequestDTO.estadoId()));

        sendero.setNombre(senderoRequestDTO.nombre());
        sendero.setDescripcion(senderoRequestDTO.descripcion());
        sendero.setDificultad(senderoRequestDTO.dificultad());
        sendero.setEstado(estado);
        sendero.setRuta(senderoMapper.coordenadasToLineString(senderoRequestDTO.coordenadas()));

        Sendero guardado = senderoRepository.save(sendero);

        Double longitud = senderoRepository.calcularLongitud(guardado.getId());
        guardado.setLongitud(longitud);
        senderoRepository.save(guardado);

        return senderoMapper.toDetalleDTO(guardado);
    }

    public void eliminar(Long id) {
        Sendero sendero = senderoRepository.findById(id)
                .orElseThrow(() -> new SenderoNotFoundException(id));
        senderoRepository.delete(sendero);
    }

}
