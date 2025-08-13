package arquitec.test.service;

import arquitec.test.dto.PedidoDto;
import arquitec.test.mapper.PedidoMapper;
import arquitec.test.model.entity.DetallePedido;
import arquitec.test.model.entity.Mesa;
import arquitec.test.model.entity.Pedido;
import arquitec.test.model.entity.Producto;
import arquitec.test.model.entity.Usuario;
import arquitec.test.model.enums.EstadoPedido;
import arquitec.test.repository.DetallePedidoRepository;
import arquitec.test.repository.MesaRepository;
import arquitec.test.repository.PedidoRepository;
import arquitec.test.repository.ProductoRepository;
import arquitec.test.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PedidoService {
    
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final MesaRepository mesaRepository;
    private final PedidoMapper pedidoMapper;
    
    public List<PedidoDto> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidoMapper.toDtoList(pedidos);
    }
    
    public List<PedidoDto> obtenerPedidosPorEstado(EstadoPedido estado) {
        List<Pedido> pedidos = pedidoRepository.findByEstado(estado);
        return pedidoMapper.toDtoList(pedidos);
    }
    
    public List<PedidoDto> obtenerPedidosCocina() {
        List<EstadoPedido> estadosCocina = List.of(EstadoPedido.PENDIENTE, EstadoPedido.EN_PREPARACION);
        List<Pedido> pedidos = pedidoRepository.findPedidosCocina(estadosCocina);
        return pedidoMapper.toDtoList(pedidos);
    }
    
    public List<PedidoDto> obtenerPedidosPorMesero(UUID meseroId) {
        List<Pedido> pedidos = pedidoRepository.findByMeseroId(meseroId);
        return pedidoMapper.toDtoList(pedidos);
    }
    
    public PedidoDto obtenerPedidoPorId(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
        return pedidoMapper.toDto(pedido);
    }
    
    public PedidoDto crearPedido(PedidoDto pedidoDto) {
        log.info("Iniciando creación de pedido: {}", pedidoDto);
        
        try {
            // Validar que el mesero existe
            Usuario mesero = usuarioRepository.findById(pedidoDto.getMeseroId())
                    .orElseThrow(() -> new RuntimeException("Mesero no encontrado con ID: " + pedidoDto.getMeseroId()));
            log.info("Mesero encontrado: {} - {}", mesero.getId(), mesero.getUsername());
            
            // Validar que la mesa existe
            Mesa mesa = mesaRepository.findById(pedidoDto.getMesaId())
                    .orElseThrow(() -> new RuntimeException("Mesa no encontrada con ID: " + pedidoDto.getMesaId()));
            log.info("Mesa encontrada: {} - {}", mesa.getId(), mesa.getNumero());
            
            // Validar que hay detalles
            if (pedidoDto.getDetalles() == null || pedidoDto.getDetalles().isEmpty()) {
                throw new RuntimeException("El pedido debe tener al menos un detalle");
            }
            log.info("Detalles del pedido: {}", pedidoDto.getDetalles().size());
            
            Pedido pedido = Pedido.builder()
                    .fecha(LocalDateTime.now())
                    .estado(EstadoPedido.PENDIENTE)
                    .mesero(mesero)
                    .mesa(mesa)
                    .build();
            
            // Agregar detalles del pedido
            for (var detalleDto : pedidoDto.getDetalles()) {
                log.info("Procesando detalle: productoId={}, cantidad={}", detalleDto.getProductoId(), detalleDto.getCantidad());
                
                Producto producto = productoRepository.findById(detalleDto.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleDto.getProductoId()));
                log.info("Producto encontrado: {} - {}", producto.getId(), producto.getNombre());
                
                DetallePedido detalle = DetallePedido.builder()
                        .cantidad(detalleDto.getCantidad())
                        .producto(producto)
                        .precioUnitario(producto.getPrecio())
                        .build();
                
                detalle.calcularSubtotal();
                pedido.agregarDetalle(detalle);
                log.info("Detalle agregado: cantidad={}, subtotal={}", detalle.getCantidad(), detalle.getSubtotal());
            }
            
            pedido.calcularTotal();
            log.info("Total del pedido calculado: {}", pedido.getTotalPedido());
            
            Pedido pedidoGuardado = pedidoRepository.save(pedido);
            log.info("Pedido guardado exitosamente con ID: {}", pedidoGuardado.getId());
            
            return pedidoMapper.toDto(pedidoGuardado);
        } catch (Exception e) {
            log.error("Error creando pedido: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    public PedidoDto actualizarEstadoPedido(UUID id, EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
        
        pedido.setEstado(nuevoEstado);
        Pedido pedidoActualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(pedidoActualizado);
    }
    
    public PedidoDto agregarProductoAlPedido(UUID pedidoId, UUID productoId, int cantidad) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + pedidoId));
        
        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new RuntimeException("No se puede modificar un pedido que ya no está pendiente");
        }
        
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));
        
        DetallePedido detalle = DetallePedido.builder()
                .cantidad(cantidad)
                .producto(producto)
                .precioUnitario(producto.getPrecio())
                .build();
        
        detalle.calcularSubtotal();
        pedido.agregarDetalle(detalle);
        pedido.calcularTotal();
        
        Pedido pedidoActualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(pedidoActualizado);
    }
    
    public void eliminarPedido(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
        
        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new RuntimeException("No se puede eliminar un pedido que ya no está pendiente");
        }
        
        pedidoRepository.deleteById(id);
    }
    
    public long contarPedidosPorEstado(EstadoPedido estado) {
        return pedidoRepository.contarPedidosPorEstado(estado);
    }
}
