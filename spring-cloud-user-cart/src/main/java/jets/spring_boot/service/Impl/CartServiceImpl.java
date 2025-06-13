package jets.spring_boot.service.Impl;

import jets.spring_boot.model.dto.BookDTO;
import jets.spring_boot.model.dto.CartDTO;
import jets.spring_boot.model.dto.CartItemDTO;
import jets.spring_boot.model.entities.CartItem;
import jets.spring_boot.model.mappers.CartMapper;
import jets.spring_boot.repo.CartItemRepo;
import jets.spring_boot.service.CartService;
import jets.spring_boot.utils.DataValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import jets.spring_boot.service.BookService;

@Service
public class CartServiceImpl implements CartService {

    private final CartItemRepo cartItemRepo;
    private final BookService bookService;
    private final CartMapper cartMapper;

    public CartServiceImpl(
            CartItemRepo cartItemRepo,
            BookService bookService,
            CartMapper cartMapper) {
        this.cartItemRepo = cartItemRepo;
        this.bookService = bookService;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartDTO getUserCart(long userId) {
        DataValidator.validateId(userId);

        List<CartItem> cartItems = cartItemRepo.findAllByUserId(userId);
        List<Long> ids = cartItems.stream()
                .map(CartItem::getBookId)
                .toList();

        List<BookDTO> booksDTOs = bookService.getAllBooks(ids);

        List<CartItemDTO> cartItemDTOs = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            BookDTO bookDTO = booksDTOs.stream()
                    .filter(book -> book.getBookId().equals(cartItem.getBookId()))
                    .findFirst()
                    .orElse(null);

            if (bookDTO == null) {
                continue;
            }

            cartItemDTOs.add(cartMapper.entityToDto(cartItem, bookDTO));
        }

        CartDTO userCart = new CartDTO();
        userCart.setUserId(userId);
        userCart.setCartItems(cartItemDTOs);

        return userCart;
    }

    @Override
    @Transactional
    public void addItemToCart(long userId, long bookId, int quantity) {
        DataValidator.validateId(userId);
        DataValidator.validateId(bookId);
        DataValidator.validateQuantity(quantity);

        CartItem cartItem = cartItemRepo.findByUserIdAndBookId(userId, bookId);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepo.save(cartItem);
            return;
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setUserId(userId);
        newCartItem.setBookId(bookId);
        newCartItem.setQuantity(quantity);

        cartItemRepo.save(newCartItem);
    }

    @Override
    @Transactional
    public void updateItemInCart(long userId, long bookId, int quantity) {
        DataValidator.validateId(userId);
        DataValidator.validateId(bookId);
        DataValidator.validateQuantity(quantity);

        CartItem foundCartItem = cartItemRepo.findByUserIdAndBookId(userId, bookId);
        if (foundCartItem == null) {
            throw new IllegalArgumentException("Cart item with bookId " + bookId + " and userId " + userId + " not found");
        }

        foundCartItem.setQuantity(quantity);
        cartItemRepo.save(foundCartItem);
    }

    @Override
    @Transactional
    public void removeItemFromCart(long userId, long bookId) {
        DataValidator.validateId(userId);
        DataValidator.validateId(bookId);

        CartItem foundCartItem = cartItemRepo.findByUserIdAndBookId(userId, bookId);
        if (foundCartItem == null) {
            throw new IllegalArgumentException("Cart item with bookId " + bookId + " and userId " + userId + " not found");
        }

        cartItemRepo.deleteByUserIdAndBookId(userId, bookId);
    }

    @Override
    @Transactional
    public void truncateCart(long userId) {
        DataValidator.validateId(userId);

        cartItemRepo.deleteByUserId(userId);
    }
}
