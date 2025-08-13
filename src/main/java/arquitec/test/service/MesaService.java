package arquitec.test.service;

import arquitec.test.dto.MesaDto;
import arquitec.test.model.entity.Mesa;
import arquitec.test.model.enums.EstadoMesa;
import arquitec.test.repository.MesaRepository;
import arquitec.test.mapper.MesaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MesaService {
    
    private final MesaRepository mesaRepository;
    private final MesaMapper mesaMapper;
    
    public List<MesaDto> obtenerTodasLasMesas() {
        List<Mesa> mesas = mesaRepository.findAllMesasActivas();
        return mesaMapper.toDtoList(mesas);
    }
    
    public List<MesaDto> obtenerMesasDisponibles() {
        List<Mesa> mesas = mesaRepository.findMesasLibres();
        return mesaMapper.toDtoList(mesas);
    }
    
    public List<MesaDto> obtenerMesasOcupadas() {
        List<Mesa> mesas = mesaRepository.findMesasOcupadas();
        return mesaMapper.toDtoList(mesas);
    }
    
    public MesaDto obtenerMesaPorId(UUID id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con ID: " + id));
        return mesaMapper.toDto(mesa);
    }
    
    public MesaDto obtenerMesaPorNumero(Integer numero) {
        Mesa mesa = mesaRepository.findByNumero(numero)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con número: " + numero));
        return mesaMapper.toDto(mesa);
    }
    
    public MesaDto crearMesa(MesaDto mesaDto) {
        // Validar que el número de mesa no exista
        if (mesaRepository.existsByNumero(mesaDto.getNumero())) {
            throw new RuntimeException("Ya existe una mesa con el número: " + mesaDto.getNumero());
        }
        
        Mesa mesa = mesaMapper.toEntity(mesaDto);
        mesa = mesaRepository.save(mesa);
        log.info("Mesa creada: {}", mesa.getNumero());
        return mesaMapper.toDto(mesa);
    }
    
    public MesaDto actualizarMesa(UUID id, MesaDto mesaDto) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con ID: " + id));
        
        mesaMapper.updateEntity(mesa, mesaDto);
        mesa = mesaRepository.save(mesa);
        log.info("Mesa actualizada: {}", mesa.getNumero());
        return mesaMapper.toDto(mesa);
    }
    
    public void eliminarMesa(UUID id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con ID: " + id));
        
        // Verificar que la mesa no tenga pedidos activos
        if (!mesa.getPedidos().isEmpty()) {
            throw new RuntimeException("No se puede eliminar una mesa con pedidos activos");
        }
        
        mesaRepository.delete(mesa);
        log.info("Mesa eliminada: {}", mesa.getNumero());
    }
    
    public MesaDto cambiarEstadoMesa(UUID id, EstadoMesa nuevoEstado) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con ID: " + id));
        
        mesa.setEstado(nuevoEstado);
        mesa = mesaRepository.save(mesa);
        log.info("Estado de mesa {} cambiado a: {}", mesa.getNumero(), nuevoEstado);
        return mesaMapper.toDto(mesa);
    }
    
    public MesaDto activarDesactivarMesa(UUID id, boolean activa) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con ID: " + id));
        
        mesa.setActiva(activa);
        if (!activa) {
            mesa.setEstado(EstadoMesa.INACTIVA);
        } else if (mesa.getEstado() == EstadoMesa.INACTIVA) {
            mesa.setEstado(EstadoMesa.DISPONIBLE);
        }
        
        mesa = mesaRepository.save(mesa);
        log.info("Mesa {} {}: {}", mesa.getNumero(), activa ? "activada" : "desactivada", mesa.getEstado());
        return mesaMapper.toDto(mesa);
    }
    
    public List<MesaDto> obtenerMesasPorEstado(EstadoMesa estado) {
        List<Mesa> mesas = mesaRepository.findByEstado(estado);
        return mesaMapper.toDtoList(mesas);
    }
}
