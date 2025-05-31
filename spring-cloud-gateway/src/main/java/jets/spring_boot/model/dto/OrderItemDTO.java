package jets.spring_boot.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItemDTO implements Serializable {

    private Long orderId;
    private BookDTO book;
    private int quantity;
}
