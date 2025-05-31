package jets.spring_boot.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "book_id")
    private Long bookId;

    private int quantity;

    public OrderItem() {}
}
