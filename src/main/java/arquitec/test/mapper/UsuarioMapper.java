package arquitec.test.mapper;

import arquitec.test.dto.LoginResponseDto;
import arquitec.test.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    
    @Mapping(target = "usuarioId", source = "id")
    LoginResponseDto toLoginResponseDto(Usuario usuario);
    
    List<LoginResponseDto> toLoginResponseDtoList(List<Usuario> usuarios);
}
