package arquitec.test.controller;

import arquitec.test.dto.ProductoDto;
import arquitec.test.model.enums.CategoriaProducto;
import arquitec.test.service.ProductoService;
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
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Gestión de productos del menú")
@SecurityRequirement(name = "bearerAuth")
public class ProductoController {
    
    private final ProductoService productoService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Lista todos los productos disponibles")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<List<ProductoDto>> obtenerTodosLosProductos() {
        List<ProductoDto> productos = productoService.obtenerTodosLosProductos();
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/disponibles")
    @Operation(summary = "Obtener productos disponibles", description = "Lista solo los productos disponibles")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<List<ProductoDto>> obtenerProductosDisponibles() {
        List<ProductoDto> productos = productoService.obtenerProductosDisponibles();
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Obtener productos por categoría", description = "Lista productos de una categoría específica")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<List<ProductoDto>> obtenerProductosPorCategoria(@PathVariable CategoriaProducto categoria) {
        List<ProductoDto> productos = productoService.obtenerProductosPorCategoria(categoria);
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Obtiene un producto específico por su ID")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<ProductoDto> obtenerProductoPorId(@PathVariable UUID id) {
        ProductoDto producto = productoService.obtenerProductoPorId(id);
        return ResponseEntity.ok(producto);
    }
    
    @PostMapping
    @Operation(summary = "Crear nuevo producto", description = "Crea un nuevo producto en el menú")
    @PreAuthorize("hasRole('DUENO')")
    public ResponseEntity<ProductoDto> crearProducto(@Valid @RequestBody ProductoDto productoDto) {
        ProductoDto producto = productoService.crearProducto(productoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente")
    @PreAuthorize("hasRole('DUENO')")
    public ResponseEntity<ProductoDto> actualizarProducto(@PathVariable UUID id, @Valid @RequestBody ProductoDto productoDto) {
        ProductoDto producto = productoService.actualizarProducto(id, productoDto);
        return ResponseEntity.ok(producto);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del menú")
    @PreAuthorize("hasRole('DUENO')")
    public ResponseEntity<Void> eliminarProducto(@PathVariable UUID id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/disponibilidad")
    @Operation(summary = "Cambiar disponibilidad", description = "Cambia la disponibilidad de un producto")
    @PreAuthorize("hasRole('DUENO')")
    public ResponseEntity<Void> cambiarDisponibilidad(@PathVariable UUID id, @RequestParam boolean disponible) {
        productoService.cambiarDisponibilidad(id, disponible);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/stats/disponibles")
    @Operation(summary = "Contar productos disponibles", description = "Obtiene el total de productos disponibles")
    @PreAuthorize("hasAnyRole('MESERO', 'DUENO')")
    public ResponseEntity<Long> contarProductosDisponibles() {
        long cantidad = productoService.contarProductosDisponibles();
        return ResponseEntity.ok(cantidad);
    }
}
