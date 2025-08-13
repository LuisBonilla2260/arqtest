package arquitec.test.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class DetallePedidoDto {
    
    private UUID id;
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
    
    private BigDecimal subtotal;
    
    @NotNull(message = "El producto es obligatorio")
    private UUID productoId;
    
    private UUID pedidoId;
    
    private BigDecimal precioUnitario;
    
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
