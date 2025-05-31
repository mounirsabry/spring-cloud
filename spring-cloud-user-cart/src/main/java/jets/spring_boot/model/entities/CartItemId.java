package jets.spring_boot.model.entities;

import lombok.Data;

@Data
public class CartItemId {
    private Long userId;
    private Long bookId;
}