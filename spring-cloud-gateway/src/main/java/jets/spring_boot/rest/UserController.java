package jets.spring_boot.rest;

import java.util.List;
import jets.spring_boot.model.dto.UserDTO;
import jets.spring_boot.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @GetMapping("/{userId}")
    ResponseEntity<UserDTO> getById(@PathVariable("userId") Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User userId cannot be null");
        }

        UserDTO dto = userService.getUserById(userId);
        return ResponseEntity.ok(dto);
    }
    
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> update(@RequestBody UserDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("User dto cannot be null");
        }
        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("User userId cannot be null");
        }


        return ResponseEntity.ok(userService.updateUser(dto));
    }
    
    @DeleteMapping("/{userId}")
    ResponseEntity<String> delete(@PathVariable("userId") Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User userId cannot be null");
        }

        return ResponseEntity.ok(userService.deleteUser(userId));
    }
    
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDTO> login(@RequestBody UserLoginDTO loginData) {
        if (loginData == null) {
            throw new IllegalArgumentException("UserLoginDTO cannot be null");
        }

        UserDTO dto = userService.login(loginData.email, loginData.password);
        return ResponseEntity.ok(dto);
    }
    
    private record UserLoginDTO(
            String email, String password) {
    }
    
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> register(@RequestBody UserRegisterDTO registerDTO) {
        if (registerDTO == null) {
            throw new IllegalArgumentException("UserRegisterDTO cannot be null");
        }

        UserDTO dto = new UserDTO();
        dto.setName(registerDTO.name);
        dto.setEmail(registerDTO.email);

        return ResponseEntity.ok(userService.register(dto, registerDTO.password));
    }
    
    private record UserRegisterDTO(
            String name, String email, String password) {
    }

    @PostMapping(value = "/changeEmail", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> changeEmail(@RequestBody ChangeEmailDTO changeEmailData) {
        if (changeEmailData == null) {
            throw new IllegalArgumentException("ChangeEmailDTO cannot be null");
        }
        if (changeEmailData.userId == null) {
            throw new IllegalArgumentException("User userId cannot be null");
        }

        String response = userService.changeEmail(
                changeEmailData.userId,
                changeEmailData.newEmail);

        return ResponseEntity.ok(response);
    }

    private record ChangeEmailDTO(Long userId, String newEmail) {}

    @PostMapping(value = "/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordData) {
        if (changePasswordData == null) {
            throw new IllegalArgumentException("ChangePasswordDTO cannot be null");
        }
        if (changePasswordData.userId == null) {
            throw new IllegalArgumentException("User userId cannot be null");
        }

        String response = userService.changePassword(
                changePasswordData.userId,
                changePasswordData.currentPassword,
                changePasswordData.newPassword);
        
        return ResponseEntity.ok(response);
    }

    private record ChangePasswordDTO(
            Long userId, String currentPassword, String newPassword) {
    }
}
