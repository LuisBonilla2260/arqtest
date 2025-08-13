package arquitec.test.controller;

import arquitec.test.dto.MesaDto;
import arquitec.test.model.enums.EstadoMesa;
import arquitec.test.service.MesaService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/mesas")
@RequiredArgsConstructor
@Tag(name = "Gestión de Mesas", description = "API para administrar mesas del restaurante")
public class MesaController {
    
    private final MesaService mesaService;
    
    @GetMapping
    @Operation(summary = "Obtener todas las mesas activas")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<List<MesaDto>> obtenerTodasLasMesas() {
        List<MesaDto> mesas = mesaService.obtenerTodasLasMesas();
        return ResponseEntity.ok(mesas);
    }
    
    @GetMapping("/disponibles")
    @Operation(summary = "Obtener mesas disponibles")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<List<MesaDto>> obtenerMesasDisponibles() {
        List<MesaDto> mesas = mesaService.obtenerMesasDisponibles();
        return ResponseEntity.ok(mesas);
    }
    
    @GetMapping("/ocupadas")
    @Operation(summary = "Obtener mesas ocupadas")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<List<MesaDto>> obtenerMesasOcupadas() {
        List<MesaDto> mesas = mesaService.obtenerMesasOcupadas();
        return ResponseEntity.ok(mesas);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener mesa por ID")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<MesaDto> obtenerMesaPorId(@PathVariable UUID id) {
        MesaDto mesa = mesaService.obtenerMesaPorId(id);
        return ResponseEntity.ok(mesa);
    }
    
    @GetMapping("/numero/{numero}")
    @Operation(summary = "Obtener mesa por número")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<MesaDto> obtenerMesaPorNumero(@PathVariable Integer numero) {
        MesaDto mesa = mesaService.obtenerMesaPorNumero(numero);
        return ResponseEntity.ok(mesa);
    }
    
    @PostMapping
    @Operation(summary = "Crear nueva mesa")
    @PreAuthorize("hasRole('DUENO')")
    public ResponseEntity<MesaDto> crearMesa(@Valid @RequestBody MesaDto mesaDto) {
        MesaDto mesaCreada = mesaService.crearMesa(mesaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mesaCreada);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar mesa existente")
    @PreAuthorize("hasRole('DUENO')")
    public ResponseEntity<MesaDto> actualizarMesa(@PathVariable UUID id, @Valid @RequestBody MesaDto mesaDto) {
        MesaDto mesaActualizada = mesaService.actualizarMesa(id, mesaDto);
        return ResponseEntity.ok(mesaActualizada);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar mesa")
    @PreAuthorize("hasRole('DUENO')")
    public ResponseEntity<Void> eliminarMesa(@PathVariable UUID id) {
        mesaService.eliminarMesa(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de mesa")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<MesaDto> cambiarEstadoMesa(@PathVariable UUID id, @RequestParam EstadoMesa nuevoEstado) {
        MesaDto mesaActualizada = mesaService.cambiarEstadoMesa(id, nuevoEstado);
        return ResponseEntity.ok(mesaActualizada);
    }
    
    @PutMapping("/{id}/activa")
    @Operation(summary = "Activar/desactivar mesa")
    @PreAuthorize("hasRole('DUENO')")
    public ResponseEntity<MesaDto> activarDesactivarMesa(@PathVariable UUID id, @RequestParam boolean activa) {
        MesaDto mesaActualizada = mesaService.activarDesactivarMesa(id, activa);
        return ResponseEntity.ok(mesaActualizada);
    }
    
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener mesas por estado")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<List<MesaDto>> obtenerMesasPorEstado(@PathVariable EstadoMesa estado) {
        List<MesaDto> mesas = mesaService.obtenerMesasPorEstado(estado);
        return ResponseEntity.ok(mesas);
    }
}

