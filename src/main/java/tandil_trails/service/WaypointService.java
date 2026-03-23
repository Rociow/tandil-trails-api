package tandil_trails.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tandil_trails.domain.Sendero;
import tandil_trails.domain.Waypoint;
import tandil_trails.dto.WaypointRequestDTO;
import tandil_trails.dto.WaypointResponseDTO;
import tandil_trails.exception.SenderoNotFoundException;
import tandil_trails.exception.WaypointNotFoundException;
import tandil_trails.exception.WaypointOrdenDuplicadoException;
import tandil_trails.mapper.WaypointMapper;
import tandil_trails.repository.SenderoRepository;
import tandil_trails.repository.WaypointRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WaypointService {

    private final WaypointRepository waypointRepository;
    private final SenderoRepository senderoRepository;
    private final WaypointMapper waypointMapper;

    @Transactional
    public WaypointResponseDTO crear(Long senderoId, WaypointRequestDTO dto) {
        Sendero sendero = senderoRepository.findById(senderoId)
                .orElseThrow(() -> new SenderoNotFoundException(senderoId));

        if (waypointRepository.existsBySenderoIdAndOrden(senderoId, dto.orden())) {
            throw new WaypointOrdenDuplicadoException(senderoId, dto.orden());
        }

        Waypoint waypoint = waypointMapper.toEntity(dto);
        waypoint.setSendero(sendero);

        return waypointMapper.toDTO(waypointRepository.save(waypoint));
    }

    public List<WaypointResponseDTO> listarPorSendero(Long senderoId) {
        if (!senderoRepository.existsById(senderoId)) {
            throw new SenderoNotFoundException(senderoId);
        }
        return waypointRepository.findBySenderoId(senderoId)
                .stream()
                .map(waypointMapper::toDTO)
                .toList();
    }

    @Transactional
    public WaypointResponseDTO actualizar(Long id, WaypointRequestDTO dto) {
        Waypoint waypoint = waypointRepository.findById(id)
                .orElseThrow(() -> new WaypointNotFoundException(id));

        if (waypoint.getOrden() != dto.orden() &&
                waypointRepository.existsBySenderoIdAndOrden(waypoint.getSendero().getId(), dto.orden())) {
            throw new WaypointOrdenDuplicadoException(waypoint.getSendero().getId(), dto.orden());
        }

        waypoint.setNombre(dto.nombre());
        waypoint.setDescripcion(dto.descripcion());
        waypoint.setOrden(dto.orden());
        waypoint.setUbicacion(waypointMapper.crearPoint(dto));

        return waypointMapper.toDTO(waypointRepository.save(waypoint));
    }

    public void eliminar(Long id) {
        Waypoint waypoint = waypointRepository.findById(id)
                .orElseThrow(() -> new WaypointNotFoundException(id));
        waypointRepository.delete(waypoint);
    }
}
