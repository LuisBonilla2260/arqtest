package arquitec.test.dto;

import arquitec.test.model.enums.RolUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    
    private String token;
    private String tipoToken = "Bearer";
    private UUID usuarioId;
    private String username;
    private RolUsuario rol;
    private String mensaje;
}
