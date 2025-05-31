package jets.spring_boot.service;

import java.util.List;
import jets.spring_boot.model.dto.UserDTO;

public interface UserService {
    
    List<UserDTO> getAllUsers();
    
    UserDTO getUserById(long userId);

    String updateUser(UserDTO userDTO);

    String deleteUser(long userId);
    
    UserDTO login(String email, String password);
    
    String register(UserDTO userDTO, String password);

    String changeEmail(long userId, String newEmail);

    String changePassword(long userId, String currentPassword, String newPassword);
}
