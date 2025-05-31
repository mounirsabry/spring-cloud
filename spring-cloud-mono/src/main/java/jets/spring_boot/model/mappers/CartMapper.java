package jets.spring_boot.model.mappers;

import jets.spring_boot.model.dto.BookDTO;
import jets.spring_boot.model.dto.CartItemDTO;
import jets.spring_boot.model.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "book", source = "book")
    @Mapping(target = "quantity", source = "entity.quantity")
    CartItemDTO entityToDto(CartItem entity, BookDTO book);

}
