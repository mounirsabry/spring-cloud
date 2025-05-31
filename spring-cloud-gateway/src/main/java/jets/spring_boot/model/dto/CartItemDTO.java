package jets.spring_boot.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartItemDTO implements Serializable {

    private BookDTO book;
    private int quantity;
}
