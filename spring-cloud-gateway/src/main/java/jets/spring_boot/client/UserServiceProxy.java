package jets.spring_boot.client;

import jets.spring_boot.model.dto.UserDTO;
import jets.spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceProxy implements UserService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public UserServiceProxy(RestTemplate restTemplate, @Value("${user_cart.service.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        String url = baseUrl + "/users";
        
        ResponseEntity<List<UserDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        
        return response.getBody();
    }

    @Override
    public UserDTO getUserById(long userId) {
        String url = baseUrl + "/users/" + userId;
        
        return restTemplate.getForObject(url, UserDTO.class);
    }

    @Override
    public String updateUser(UserDTO userDTO) {
        String url = baseUrl + "/users";

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(userDTO),
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @Override
    public String deleteUser(long userId) {
        String url = baseUrl + "/users/" + userId;

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<>() {}
        );
        
        return response.getBody();
    }

    @Override
    public UserDTO login(String email, String password) {
        String url = baseUrl + "/users/login";
        
        return restTemplate.postForObject(url, new LoginDTO(email, password), UserDTO.class);
    }
    
    private record LoginDTO(String email, String password) {}

    @Override
    public String register(UserDTO userDTO, String password) {
        String url = baseUrl + "/users/register";
        
        RegisterDTO registerDTO = new RegisterDTO(
            userDTO.getName(),
            userDTO.getEmail(),
            password    
        );
        
        return restTemplate.postForObject(url, registerDTO, String.class);
    }
    
    private record RegisterDTO(String name, String email, String password) {}

    @Override
    public String changeEmail(long userId, String newEmail) {
        String url = baseUrl + "/users/changeEmail";

        ChangeEmailDTO changeEmailDTO = new ChangeEmailDTO(userId, newEmail);

        return restTemplate.postForObject(url, changeEmailDTO, String.class);
    }

    private record ChangeEmailDTO(Long userId, String newEmail) {}

    @Override
    public String changePassword(long userId, String currentPassword, String newPassword) {
        String url = baseUrl + "/users/changePassword";

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(userId, currentPassword, newPassword);

        return restTemplate.postForObject(url, changePasswordDTO, String.class);
    }

    private record ChangePasswordDTO(
            Long userId, String currentPassword, String newPassword) {
    }
}
