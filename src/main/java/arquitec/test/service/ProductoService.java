package arquitec.test.service;

import arquitec.test.dto.ProductoDto;
import arquitec.test.mapper.ProductoMapper;
import arquitec.test.model.entity.Producto;
import arquitec.test.model.enums.CategoriaProducto;
import arquitec.test.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductoService {
    
    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    
    public List<ProductoDto> obtenerTodosLosProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productoMapper.toDtoList(productos);
    }
    
    public List<ProductoDto> obtenerProductosDisponibles() {
        List<Producto> productos = productoRepository.findByDisponibleTrue();
        return productoMapper.toDtoList(productos);
    }
    
    public List<ProductoDto> obtenerProductosPorCategoria(CategoriaProducto categoria) {
        List<Producto> productos = productoRepository.findByCategoriaAndDisponibleTrue(categoria);
        return productoMapper.toDtoList(productos);
    }
    
    public ProductoDto obtenerProductoPorId(UUID id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return productoMapper.toDto(producto);
    }
    
    public ProductoDto crearProducto(ProductoDto productoDto) {
        if (productoRepository.existsByNombreIgnoreCase(productoDto.getNombre())) {
            throw new RuntimeException("Ya existe un producto con el nombre: " + productoDto.getNombre());
        }
        
        Producto producto = productoMapper.toEntity(productoDto);
        Producto productoGuardado = productoRepository.save(producto);
        return productoMapper.toDto(productoGuardado);
    }
    
    public ProductoDto actualizarProducto(UUID id, ProductoDto productoDto) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        
        productoMapper.updateEntity(productoExistente, productoDto);
        Producto productoActualizado = productoRepository.save(productoExistente);
        return productoMapper.toDto(productoActualizado);
    }
    
    public void eliminarProducto(UUID id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }
    
    public void cambiarDisponibilidad(UUID id, boolean disponible) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        producto.setDisponible(disponible);
        productoRepository.save(producto);
    }
    
    public long contarProductosDisponibles() {
        return productoRepository.contarProductosDisponibles();
    }
}
