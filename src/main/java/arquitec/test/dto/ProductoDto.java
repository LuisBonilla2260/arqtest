package arquitec.test.dto;

import arquitec.test.model.enums.CategoriaProducto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDto {
    
    private UUID id;
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Digits(integer = 8, fraction = 2, message = "El precio debe tener máximo 8 dígitos enteros y 2 decimales")
    private BigDecimal precio;
    
    @NotNull(message = "La categoría es obligatoria")
    private CategoriaProducto categoria;
    
    private boolean disponible = true;
    
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
