package arquitec.test.repository;

import arquitec.test.model.entity.Usuario;
import arquitec.test.model.enums.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    
    Optional<Usuario> findByUsername(String username);
    
    Optional<Usuario> findByUsernameAndActivoTrue(String username);
    
    boolean existsByUsername(String username);
    
    List<Usuario> findByRol(RolUsuario rol);
    
    List<Usuario> findByRolAndActivoTrue(RolUsuario rol);
    
    @Query("SELECT u FROM Usuario u WHERE u.rol = :rol AND u.activo = true")
    List<Usuario> findMeserosActivos(@Param("rol") RolUsuario rol);
}
