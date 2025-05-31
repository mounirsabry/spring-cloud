package jets.spring_boot.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CartDTO implements Serializable {

    private Long userId;
    private List<CartItemDTO> cartItems;
}
