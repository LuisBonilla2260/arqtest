package arquitec.test.mapper;

import arquitec.test.dto.DetallePedidoDto;
import arquitec.test.model.entity.DetallePedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetallePedidoMapper {
    
    @Mapping(target = "productoId", source = "producto.id")
    @Mapping(target = "pedidoId", source = "pedido.id")
    DetallePedidoDto toDto(DetallePedido detallePedido);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    DetallePedido toEntity(DetallePedidoDto detallePedidoDto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    void updateEntity(@MappingTarget DetallePedido detallePedido, DetallePedidoDto detallePedidoDto);
    
    List<DetallePedidoDto> toDtoList(List<DetallePedido> detallesPedido);
    
    List<DetallePedido> toEntityList(List<DetallePedidoDto> detallePedidoDtos);
}
