package jets.spring_boot.repo;

import jets.spring_boot.model.entities.CartItem;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CartItemRepo extends Repository<CartItem, Long> {

    List<CartItem> findAllByUserId(long userId);

    CartItem findByUserIdAndBookId(long userId, long bookId);

    CartItem save(CartItem cartItem);

    void deleteByUserIdAndBookId(long userId, long bookId);

    void deleteByUserId(long userId);
}
