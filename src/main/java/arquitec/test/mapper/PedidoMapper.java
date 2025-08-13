package arquitec.test.mapper;

import arquitec.test.dto.PedidoDto;
import arquitec.test.model.entity.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DetallePedidoMapper.class})
public interface PedidoMapper {
    
    @Mapping(target = "meseroId", source = "mesero.id")
    @Mapping(target = "mesaId", source = "mesa.id")
    @Mapping(target = "detalles", source = "detalles")
    PedidoDto toDto(Pedido pedido);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "totalPedido", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "mesero", ignore = true)
    @Mapping(target = "mesa", ignore = true)
    Pedido toEntity(PedidoDto pedidoDto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "totalPedido", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "mesero", ignore = true)
    @Mapping(target = "mesa", ignore = true)
    void updateEntity(@MappingTarget Pedido pedido, PedidoDto pedidoDto);
    
    List<PedidoDto> toDtoList(List<Pedido> pedidos);
    
    List<Pedido> toEntityList(List<PedidoDto> pedidoDtos);
}
