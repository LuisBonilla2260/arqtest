# 🍕 Sistema de Gestión de Pedidos - Restaurante

Sistema backend completo para la gestión de pedidos en un restaurante de comida rápida, desarrollado con Spring Boot 3.x y Java 21.

## 🚀 Características Principales

- **Gestión de Productos**: CRUD completo para comidas y bebidas
- **Sistema de Pedidos**: Creación, modificación y seguimiento de pedidos
- **Roles de Usuario**: Meseros y Dueños con permisos diferenciados
- **Autenticación JWT**: Sistema seguro de autenticación
- **API REST**: Endpoints bien documentados con Swagger
- **Base de Datos PostgreSQL**: Persistencia robusta de datos
- **Validaciones**: DTOs con validaciones completas
- **Mapeo de Objetos**: MapStruct para conversiones eficientes

## 🛠️ Tecnologías Utilizadas

- **Java 21** - Lenguaje de programación
- **Spring Boot 3.5.4** - Framework principal
- **Spring Security** - Seguridad y autenticación
- **Spring Data JPA** - Persistencia de datos
- **PostgreSQL** - Base de datos
- **JWT** - Tokens de autenticación
- **MapStruct** - Mapeo de objetos
- **Lombok** - Reducción de código boilerplate
- **Swagger/OpenAPI** - Documentación de API
- **Maven** - Gestión de dependencias

## 📋 Requisitos Previos

- Java 21 o superior
- Maven 3.6+
- PostgreSQL 12+ (puerto 9000)
- IDE compatible con Java (IntelliJ IDEA, Eclipse, VS Code)

## 🗄️ Configuración de Base de Datos

1. **Instalar PostgreSQL** en el puerto 9000
2. **Crear base de datos**:
   ```sql
   CREATE DATABASE restaurante;
   ```
3. **Configurar credenciales** en `application.properties`:
   - Usuario: `postgres`
   - Contraseña: `123456`
   - Puerto: `9000`
   - Base de datos: `restaurante`

## 🚀 Instalación y Ejecución

### 1. Clonar el Proyecto
```bash
git clone <url-del-repositorio>
cd restaurante-pedidos
```

### 2. Configurar Base de Datos
- Asegúrate de que PostgreSQL esté ejecutándose en el puerto 9000
- Crea la base de datos `restaurante`
- Verifica las credenciales en `application.properties`

### 3. Compilar y Ejecutar
```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar la aplicación
mvn spring-boot:run
```

### 4. Verificar la Aplicación
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Documentación API**: http://localhost:8080/api-docs

## 👥 Usuarios de Prueba

La aplicación se inicializa automáticamente con los siguientes usuarios:

### Dueño (Administrador)
- **Usuario**: `admin`
- **Contraseña**: `admin123`
- **Rol**: `DUENO`
- **Permisos**: Acceso completo a todas las funcionalidades

### Meseros
- **Usuario**: `mesero1`
- **Contraseña**: `mesero123`
- **Rol**: `MESERO`
- **Permisos**: Gestión de pedidos y consulta de productos

- **Usuario**: `mesero2`
- **Contraseña**: `mesero123`
- **Rol**: `MESERO`
- **Permisos**: Gestión de pedidos y consulta de productos

## 🔐 Autenticación

### 1. Obtener Token JWT
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

### 2. Usar Token en Requests
```http
Authorization: Bearer <token-jwt>
```

## 📚 Endpoints Principales

### Autenticación
- `POST /api/auth/login` - Iniciar sesión

### Productos
- `GET /api/productos` - Listar todos los productos
- `GET /api/productos/disponibles` - Productos disponibles
- `GET /api/productos/categoria/{categoria}` - Productos por categoría
- `POST /api/productos` - Crear producto (solo DUENO)
- `PUT /api/productos/{id}` - Actualizar producto (solo DUENO)
- `DELETE /api/productos/{id}` - Eliminar producto (solo DUENO)

### Pedidos
- `GET /api/pedidos` - Listar todos los pedidos
- `GET /api/pedidos/cocina` - Pedidos para cocina
- `POST /api/pedidos` - Crear pedido (solo MESERO)
- `PATCH /api/pedidos/{id}/estado` - Actualizar estado
- `POST /api/pedidos/{id}/productos` - Agregar producto al pedido

## 🏗️ Arquitectura del Proyecto

```
src/main/java/arquitec/test/
├── config/                 # Configuraciones
│   ├── SecurityConfig.java
│   ├── AuthenticationConfig.java
│   └── OpenApiConfig.java
├── controller/             # Controladores REST
│   ├── AuthController.java
│   ├── ProductoController.java
│   └── PedidoController.java
├── dto/                   # Objetos de transferencia
│   ├── ProductoDto.java
│   ├── PedidoDto.java
│   └── DetallePedidoDto.java
├── entity/                 # Entidades JPA
│   ├── Producto.java
│   ├── Pedido.java
│   ├── DetallePedido.java
│   └── Usuario.java
├── enums/                  # Enumeraciones
│   ├── CategoriaProducto.java
│   ├── EstadoPedido.java
│   └── RolUsuario.java
├── mapper/                 # Mappers MapStruct
│   ├── ProductoMapper.java
│   ├── PedidoMapper.java
│   └── DetallePedidoMapper.java
├── repository/             # Repositorios JPA
│   ├── ProductoRepository.java
│   ├── PedidoRepository.java
│   └── UsuarioRepository.java
└── service/                # Servicios de negocio
    ├── ProductoService.java
    ├── PedidoService.java
    └── AuthenticationService.java
```

## 🔧 Configuración Personalizada

### Cambiar Puerto de la Aplicación
```properties
# application.properties
server.port=8081
```

### Cambiar Configuración de Base de Datos
```properties
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mi_restaurante
spring.datasource.username=mi_usuario
spring.datasource.password=mi_contraseña
```

### Configurar JWT
```properties
# application.properties
jwt.secret=miClaveSecretaPersonalizada
jwt.expiration=3600000
```

## 🧪 Pruebas

### Ejecutar Tests
```bash
mvn test
```

### Ejecutar Tests con Cobertura
```bash
mvn test jacoco:report
```

## 📊 Monitoreo y Logs

La aplicación incluye logging detallado para:
- Operaciones de base de datos
- Autenticación y autorización
- Operaciones de negocio
- Errores y excepciones

### Niveles de Log
- `DEBUG`: Información detallada de Spring Security y Hibernate
- `INFO`: Operaciones principales del sistema
- `ERROR`: Errores y excepciones

## 🚨 Solución de Problemas

### Error de Conexión a Base de Datos
1. Verificar que PostgreSQL esté ejecutándose
2. Confirmar credenciales en `application.properties`
3. Verificar que la base de datos `restaurante` exista

### Error de Compilación MapStruct
1. Ejecutar `mvn clean compile`
2. Verificar que Lombok esté habilitado en el IDE
3. Asegurar que las anotaciones de MapStruct estén correctas

### Error de Autenticación
1. Verificar que el usuario exista en la base de datos
2. Confirmar que la contraseña sea correcta
3. Verificar que el usuario esté activo

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 📞 Soporte

Para soporte técnico o consultas:
- **Email**: desarrollo@restaurante.com
- **Documentación**: http://localhost:8080/swagger-ui.html
- **Issues**: Crear un issue en el repositorio

## 🔮 Roadmap

- [ ] Implementación de WebSockets para actualizaciones en tiempo real
- [ ] Sistema de notificaciones push
- [ ] Reportes y analytics
- [ ] Integración con sistemas de pago
- [ ] App móvil complementaria
- [ ] Dashboard administrativo

---

**¡Disfruta desarrollando con este sistema de gestión de restaurante! 🎉**
"# arqtest" 
