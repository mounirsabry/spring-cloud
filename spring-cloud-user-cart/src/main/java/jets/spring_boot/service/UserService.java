package jets.spring_boot.service;

import java.util.List;
import jets.spring_boot.model.dto.UserDTO;

public interface UserService {
    
    List<UserDTO> getAllUsers();
    
    UserDTO getUserById(long userId);
    
    void updateUser(UserDTO userDTO);
    
    void deleteUser(long userId);
    
    UserDTO login(String email, String password);
    
    void register(UserDTO userDTO, String password);

    void changeEmail(long userId, String newEmail);
    
    void changePassword(long userId, String currentPassword, String newPassword);
}
