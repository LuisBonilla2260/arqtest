package arquitec.test.mapper;

import arquitec.test.dto.MesaDto;
import arquitec.test.model.entity.Mesa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MesaMapper {
    
    MesaDto toDto(Mesa mesa);
    
    Mesa toEntity(MesaDto mesaDto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "pedidos", ignore = true)
    void updateEntity(@MappingTarget Mesa mesa, MesaDto mesaDto);
    
    List<MesaDto> toDtoList(List<Mesa> mesas);
    
    List<Mesa> toEntityList(List<MesaDto> mesaDtos);
}

