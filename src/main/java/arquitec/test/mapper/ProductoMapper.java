package arquitec.test.mapper;

import arquitec.test.dto.ProductoDto;
import arquitec.test.model.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    
    @Mapping(target = "id", source = "id")
    ProductoDto toDto(Producto producto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    Producto toEntity(ProductoDto productoDto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    void updateEntity(@MappingTarget Producto producto, ProductoDto productoDto);
    
    List<ProductoDto> toDtoList(List<Producto> productos);
    
    List<Producto> toEntityList(List<ProductoDto> productoDtos);
}
