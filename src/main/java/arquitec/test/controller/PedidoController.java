package arquitec.test.controller;

import arquitec.test.dto.PedidoDto;
import arquitec.test.model.enums.EstadoPedido;
import arquitec.test.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Gestión de pedidos del restaurante")
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {
    
    private final PedidoService pedidoService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los pedidos", description = "Lista todos los pedidos")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<List<PedidoDto>> obtenerTodosLosPedidos() {
        List<PedidoDto> pedidos = pedidoService.obtenerTodosLosPedidos();
        return ResponseEntity.ok(pedidos);
    }
    
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener pedidos por estado", description = "Lista pedidos de un estado específico")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<List<PedidoDto>> obtenerPedidosPorEstado(@PathVariable EstadoPedido estado) {
        List<PedidoDto> pedidos = pedidoService.obtenerPedidosPorEstado(estado);
        return ResponseEntity.ok(pedidos);
    }
    
    @GetMapping("/cocina")
    @Operation(summary = "Obtener pedidos para cocina", description = "Lista pedidos pendientes y en preparación")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<List<PedidoDto>> obtenerPedidosCocina() {
        List<PedidoDto> pedidos = pedidoService.obtenerPedidosCocina();
        return ResponseEntity.ok(pedidos);
    }
    
    @GetMapping("/mesero/{meseroId}")
    @Operation(summary = "Obtener pedidos por mesero", description = "Lista pedidos de un mesero específico")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<List<PedidoDto>> obtenerPedidosPorMesero(@PathVariable UUID meseroId) {
        List<PedidoDto> pedidos = pedidoService.obtenerPedidosPorMesero(meseroId);
        return ResponseEntity.ok(pedidos);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID", description = "Obtiene un pedido específico por su ID")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<PedidoDto> obtenerPedidoPorId(@PathVariable UUID id) {
        PedidoDto pedido = pedidoService.obtenerPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }
    
    @PostMapping
    @Operation(summary = "Crear nuevo pedido", description = "Crea un nuevo pedido")
    @PreAuthorize("hasRole('MESERO')")
    public ResponseEntity<PedidoDto> crearPedido(@Valid @RequestBody PedidoDto pedidoDto) {
        PedidoDto pedido = pedidoService.crearPedido(pedidoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }
    
    @PatchMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado del pedido", description = "Actualiza el estado de un pedido")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<PedidoDto> actualizarEstadoPedido(@PathVariable UUID id, @RequestParam EstadoPedido nuevoEstado) {
        PedidoDto pedido = pedidoService.actualizarEstadoPedido(id, nuevoEstado);
        return ResponseEntity.ok(pedido);
    }
    
    @PostMapping("/{id}/productos")
    @Operation(summary = "Agregar producto al pedido", description = "Agrega un producto a un pedido existente")
    @PreAuthorize("hasRole('MESERO')")
    public ResponseEntity<PedidoDto> agregarProductoAlPedido(
            @PathVariable UUID id,
            @RequestParam UUID productoId,
            @RequestParam int cantidad) {
        PedidoDto pedido = pedidoService.agregarProductoAlPedido(id, productoId, cantidad);
        return ResponseEntity.ok(pedido);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido pendiente")
    @PreAuthorize("hasRole('MESERO')")
    public ResponseEntity<Void> eliminarPedido(@PathVariable UUID id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/stats/estado/{estado}")
    @Operation(summary = "Contar pedidos por estado", description = "Obtiene el total de pedidos de un estado")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<Long> contarPedidosPorEstado(@PathVariable EstadoPedido estado) {
        long cantidad = pedidoService.contarPedidosPorEstado(estado);
        return ResponseEntity.ok(cantidad);
    }
}
