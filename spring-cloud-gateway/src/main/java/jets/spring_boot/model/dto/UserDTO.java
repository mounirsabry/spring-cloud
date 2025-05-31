package jets.spring_boot.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserDTO implements Serializable {
    
    private Long userId;
    private String name;
    private String email;
}
