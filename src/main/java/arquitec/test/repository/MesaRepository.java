package arquitec.test.repository;

import arquitec.test.model.entity.Mesa;
import arquitec.test.model.enums.EstadoMesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, UUID> {
    
    Optional<Mesa> findByNumero(Integer numero);
    
    boolean existsByNumero(Integer numero);
    
    List<Mesa> findByEstado(EstadoMesa estado);
    
    List<Mesa> findByActivaTrue();
    
    @Query("SELECT m FROM Mesa m WHERE m.estado = :estado AND m.activa = true")
    List<Mesa> findMesasDisponibles(@Param("estado") EstadoMesa estado);
    
    @Query("SELECT m FROM Mesa m WHERE m.activa = true ORDER BY m.numero")
    List<Mesa> findAllMesasActivas();
    
    @Query("SELECT m FROM Mesa m WHERE m.estado = 'DISPONIBLE' AND m.activa = true ORDER BY m.numero")
    List<Mesa> findMesasLibres();
    
    @Query("SELECT m FROM Mesa m WHERE m.estado = 'OCUPADA' AND m.activa = true ORDER BY m.numero")
    List<Mesa> findMesasOcupadas();
}

