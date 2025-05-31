package jets.spring_boot.service;

import jets.spring_boot.model.dto.CartDTO;

public interface CartService {

    CartDTO getUserCart(Long userId);

    String addItemToCart(Long userId, Long bookId, int quantity);

    String updateItemInCart(Long userId, Long bookId, int quantity);

    String removeItemFromCart(Long userId, Long bookId);

    String truncateCart(Long userId);
}
