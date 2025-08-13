package arquitec.test.dto;

import arquitec.test.model.enums.EstadoMesa;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MesaDto {
    
    private UUID id;
    
    @NotNull(message = "El número de mesa es obligatorio")
    @Min(value = 1, message = "El número de mesa debe ser mayor a 0")
    private Integer numero;
    
    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser mayor a 0")
    private Integer capacidad;
    
    @NotNull(message = "El estado es obligatorio")
    private EstadoMesa estado;
    
    @Size(max = 500, message = "La ubicación no puede exceder 500 caracteres")
    private String ubicacion;
    
    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activa;
    
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}

