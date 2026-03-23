package tandil_trails.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tandil_trails.domain.EstadoSendero;
import tandil_trails.dto.estadoSendero.EstadoSenderoRequestDTO;
import tandil_trails.dto.estadoSendero.EstadoSenderoResponseDTO;
import tandil_trails.exception.EstadoSenderoNotFoundException;
import tandil_trails.mapper.EstadoSenderoMapper;
import tandil_trails.repository.EstadoSenderoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoSenderoService {

    private final EstadoSenderoRepository estadoSenderoRepository;
    private final EstadoSenderoMapper estadoSenderoMapper;

    public List<EstadoSenderoResponseDTO> listarTodos() {
        return estadoSenderoRepository.findAll()
                .stream()
                .map(estadoSenderoMapper::toDTO)
                .toList();
    }

    @Transactional
    public EstadoSenderoResponseDTO crear(EstadoSenderoRequestDTO dto) {
        EstadoSendero estado = estadoSenderoMapper.toEntity(dto);
        return estadoSenderoMapper.toDTO(estadoSenderoRepository.save(estado));
    }

    public void eliminar(Long id) {
        EstadoSendero estado = estadoSenderoRepository.findById(id)
                .orElseThrow(() -> new EstadoSenderoNotFoundException(id));
        estadoSenderoRepository.delete(estado);
    }
}
