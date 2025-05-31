package jets.spring_boot.service;

import jets.spring_boot.model.dto.CartDTO;

public interface CartService {

    CartDTO getUserCart(long userId);

    void addItemToCart(long userId, long bookId, int quantity);

    void updateItemInCart(long userId, long bookId, int quantity);

    void removeItemFromCart(long userId, long bookId);

    void truncateCart(long userId);
}
