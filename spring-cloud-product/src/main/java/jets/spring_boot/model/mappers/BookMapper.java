package jets.spring_boot.model.mappers;

import jets.spring_boot.model.dto.BookDTO;
import jets.spring_boot.model.entities.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book dtoToEntity(BookDTO dto);
    
    BookDTO entityToDto(Book entity);
}
