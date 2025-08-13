package arquitec.test.dto;

import arquitec.test.model.enums.EstadoPedido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDto {
    
    private UUID id;
    
    @NotNull(message = "La fecha del pedido es obligatoria")
    private LocalDateTime fecha;
    
    @NotNull(message = "El estado del pedido es obligatorio")
    private EstadoPedido estado;
    
    @NotNull(message = "El mesero es obligatorio")
    private UUID meseroId;
    
    @NotNull(message = "La mesa es obligatoria")
    private UUID mesaId;
    
    private BigDecimal totalPedido;
    
    @NotNull(message = "Los detalles del pedido son obligatorios")
    private List<DetallePedidoDto> detalles;
    
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
