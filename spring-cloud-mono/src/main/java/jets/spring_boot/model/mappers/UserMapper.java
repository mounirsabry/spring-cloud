package jets.spring_boot.model.mappers;

import jets.spring_boot.model.dto.UserDTO;
import jets.spring_boot.model.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToEntity(UserDTO dto);
    
    UserDTO entityToDto(User entity);
}
