package arquitec.test.repository;

import arquitec.test.model.entity.Pedido;
import arquitec.test.model.enums.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    
    List<Pedido> findByEstado(EstadoPedido estado);
    
    List<Pedido> findByEstadoIn(List<EstadoPedido> estados);
    
    List<Pedido> findByMeseroId(UUID meseroId);
    
    List<Pedido> findByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    @Query("SELECT p FROM Pedido p WHERE p.estado IN (:estados) ORDER BY p.fecha ASC")
    List<Pedido> findPedidosCocina(@Param("estados") List<EstadoPedido> estados);
    
    @Query("SELECT p FROM Pedido p WHERE p.mesero.id = :meseroId AND p.fecha >= :fechaInicio ORDER BY p.fecha DESC")
    List<Pedido> findPedidosMeseroPorFecha(@Param("meseroId") UUID meseroId, @Param("fechaInicio") LocalDateTime fechaInicio);
    
    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.estado = :estado")
    long contarPedidosPorEstado(@Param("estado") EstadoPedido estado);
}
