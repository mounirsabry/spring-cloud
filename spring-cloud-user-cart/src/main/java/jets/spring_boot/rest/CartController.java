package jets.spring_boot.rest;

import jets.spring_boot.model.dto.CartDTO;
import jets.spring_boot.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public CartDTO getUserCart(@PathVariable("userId") Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User userId cannot be null");
        }

        return cartService.getUserCart(userId);
    }

    @PostMapping
    ResponseEntity<String> addItemToCart(@RequestBody UserCartItemDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("UserCartItemDTO cannot be null");
        }
        if (dto.userId() == null) {
            throw new IllegalArgumentException("User userId cannot be null");
        }

        cartService.addItemToCart(
                dto.userId(),
                dto.bookId(),
                dto.quantity()
        );

        return ResponseEntity.ok("Item was added to cart");
    }

    @PutMapping
    ResponseEntity<String> updateItemInCart(@RequestBody UserCartItemDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("UserCartItemDTO cannot be null");
        }
        if (dto.userId() == null) {
            throw new IllegalArgumentException("User userId cannot be null");
        }

        cartService.updateItemInCart(
                dto.userId(),
                dto.bookId(),
                dto.quantity()
        );

        return ResponseEntity.ok("Item was updated in cart");
    }

    private record UserCartItemDTO(Long userId, Long bookId, int quantity) {}

    @DeleteMapping
    ResponseEntity<String> deleteItemFromCart(@RequestBody DeleteCartItemDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("DeleteCartItemDTO cannot be null");
        }
        if (dto.userId() == null) {
            throw new IllegalArgumentException("User userId cannot be null");
        }

        cartService.removeItemFromCart(
                dto.userId(),
                dto.bookId()
        );

        return ResponseEntity.ok("Item was deleted from cart");
    }

    private record DeleteCartItemDTO(Long userId, Long bookId) {}

    @DeleteMapping("/{userId}")
    ResponseEntity<String> truncateCart(@PathVariable("userId") Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User userId cannot be null");
        }

        cartService.truncateCart(userId);

        return ResponseEntity.ok("Cart was truncated");
    }
}
