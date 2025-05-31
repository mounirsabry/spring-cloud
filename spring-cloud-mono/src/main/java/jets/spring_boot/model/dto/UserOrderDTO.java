package jets.spring_boot.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserOrderDTO implements Serializable {

    private Long orderId;
    private Long userId;
    private double totalPrice;
    private List<OrderItemDTO> orderItems;
}
