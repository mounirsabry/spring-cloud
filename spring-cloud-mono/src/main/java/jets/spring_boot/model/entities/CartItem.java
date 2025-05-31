package jets.spring_boot.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_cart_item")
@IdClass(CartItemId.class)
public class CartItem {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(nullable = false)
    private int quantity;

    public CartItem() {}
}
