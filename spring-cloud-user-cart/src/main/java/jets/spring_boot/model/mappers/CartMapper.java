package jets.spring_boot.model.mappers;

import jets.spring_boot.model.dto.BookDTO;
import jets.spring_boot.model.dto.CartItemDTO;
import jets.spring_boot.model.entities.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartItemDTO entityToDto(CartItem entity, BookDTO book);

}
