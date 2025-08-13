package arquitec.test.repository;

import arquitec.test.model.entity.Producto;
import arquitec.test.model.enums.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, UUID> {
    
    Optional<Producto> findByNombreIgnoreCase(String nombre);
    
    List<Producto> findByCategoria(CategoriaProducto categoria);
    
    List<Producto> findByDisponibleTrue();
    
    List<Producto> findByCategoriaAndDisponibleTrue(CategoriaProducto categoria);
    
    @Query("SELECT p FROM Producto p WHERE p.nombre ILIKE %:termino% OR p.categoria = :categoria")
    List<Producto> buscarPorTerminoOCategoria(@Param("termino") String termino, @Param("categoria") CategoriaProducto categoria);
    
    boolean existsByNombreIgnoreCase(String nombre);
    
    @Query("SELECT COUNT(p) FROM Producto p WHERE p.disponible = true")
    long contarProductosDisponibles();
}
