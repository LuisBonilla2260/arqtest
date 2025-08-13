package arquitec.test.service;

import arquitec.test.model.entity.Mesa;
import arquitec.test.model.entity.Producto;
import arquitec.test.model.entity.Usuario;
import arquitec.test.model.enums.CategoriaProducto;
import arquitec.test.model.enums.EstadoMesa;
import arquitec.test.model.enums.RolUsuario;
import arquitec.test.repository.MesaRepository;
import arquitec.test.repository.ProductoRepository;
import arquitec.test.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataInitializationService implements CommandLineRunner {
    
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final MesaRepository mesaRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("Inicializando datos de prueba...");
        
        // Crear usuarios de prueba
        crearUsuariosPrueba();
        
        // Crear productos de prueba
        crearProductosPrueba();
        
        // Crear mesas de prueba
        crearMesasPrueba();
        
        log.info("Datos de prueba inicializados correctamente");
    }
    
    private void crearUsuariosPrueba() {
        if (usuarioRepository.count() == 0) {
            List<Usuario> usuarios = Arrays.asList(
                    Usuario.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("admin123"))
                            .rol(RolUsuario.DUENO)
                            .activo(true)
                            .build(),
                    Usuario.builder()
                            .username("mesero1")
                            .password(passwordEncoder.encode("mesero123"))
                            .rol(RolUsuario.MESERO)
                            .activo(true)
                            .build(),
                    Usuario.builder()
                            .username("mesero2")
                            .password(passwordEncoder.encode("mesero123"))
                            .rol(RolUsuario.MESERO)
                            .activo(true)
                            .build()
            );
            
            usuarioRepository.saveAll(usuarios);
            log.info("Usuarios de prueba creados: admin, mesero1, mesero2");
        }
    }
    
    private void crearProductosPrueba() {
        if (productoRepository.count() == 0) {
            List<Producto> productos = Arrays.asList(
                    // Comidas
                    Producto.builder()
                            .nombre("Hamburguesa Clásica")
                            .precio(new BigDecimal("8.99"))
                            .categoria(CategoriaProducto.COMIDA)
                            .disponible(true)
                            .build(),
                    Producto.builder()
                            .nombre("Pizza Margherita")
                            .precio(new BigDecimal("12.99"))
                            .categoria(CategoriaProducto.COMIDA)
                            .disponible(true)
                            .build(),
                    Producto.builder()
                            .nombre("Ensalada César")
                            .precio(new BigDecimal("6.99"))
                            .categoria(CategoriaProducto.COMIDA)
                            .disponible(true)
                            .build(),
                    Producto.builder()
                            .nombre("Pasta Carbonara")
                            .precio(new BigDecimal("10.99"))
                            .categoria(CategoriaProducto.COMIDA)
                            .disponible(true)
                            .build(),
                    
                    // Bebidas
                    Producto.builder()
                            .nombre("Coca Cola")
                            .precio(new BigDecimal("2.50"))
                            .categoria(CategoriaProducto.BEBIDA)
                            .disponible(true)
                            .build(),
                    Producto.builder()
                            .nombre("Agua Mineral")
                            .precio(new BigDecimal("1.50"))
                            .categoria(CategoriaProducto.BEBIDA)
                            .disponible(true)
                            .build(),
                    Producto.builder()
                            .nombre("Jugo de Naranja")
                            .precio(new BigDecimal("3.00"))
                            .categoria(CategoriaProducto.BEBIDA)
                            .disponible(true)
                            .build(),
                    Producto.builder()
                            .nombre("Cerveza")
                            .precio(new BigDecimal("4.50"))
                            .categoria(CategoriaProducto.BEBIDA)
                            .disponible(true)
                            .build()
            );
            
            productoRepository.saveAll(productos);
            log.info("Productos de prueba creados: {} productos", productos.size());
        }
    }
    
    private void crearMesasPrueba() {
        if (mesaRepository.count() == 0) {
            List<Mesa> mesas = Arrays.asList(
                    Mesa.builder()
                            .numero(1)
                            .capacidad(4)
                            .estado(EstadoMesa.DISPONIBLE)
                            .ubicacion("Terraza")
                            .activa(true)
                            .build(),
                    Mesa.builder()
                            .numero(2)
                            .capacidad(6)
                            .estado(EstadoMesa.DISPONIBLE)
                            .ubicacion("Interior")
                            .activa(true)
                            .build(),
                    Mesa.builder()
                            .numero(3)
                            .capacidad(2)
                            .estado(EstadoMesa.DISPONIBLE)
                            .ubicacion("Barra")
                            .activa(true)
                            .build(),
                    Mesa.builder()
                            .numero(4)
                            .capacidad(8)
                            .estado(EstadoMesa.DISPONIBLE)
                            .ubicacion("Sala principal")
                            .activa(true)
                            .build(),
                    Mesa.builder()
                            .numero(5)
                            .capacidad(4)
                            .estado(EstadoMesa.DISPONIBLE)
                            .ubicacion("Interior")
                            .activa(true)
                            .build()
            );
            
            mesaRepository.saveAll(mesas);
            log.info("Mesas de prueba creadas: 5 mesas con capacidades 2-8 personas");
        }
    }
}
