package jets.spring_boot.client;

import jets.spring_boot.model.dto.CartDTO;
import jets.spring_boot.service.CartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartServiceProxy implements CartService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public CartServiceProxy(RestTemplate restTemplate, @Value("${user_cart.service.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public CartDTO getUserCart(Long userId) {
        String url = baseUrl + "/cart/" + userId;

        return restTemplate.getForObject(url, CartDTO.class);
    }

    @Override
    public String addItemToCart(Long userId, Long bookId, int quantity) {
        String url = baseUrl + "/cart";

        AddOrUpdateCartItemDTO dto = new AddOrUpdateCartItemDTO(userId, bookId, quantity);

        return restTemplate.postForObject(url, dto, String.class);
    }

    @Override
    public String updateItemInCart(Long userId, Long bookId, int quantity) {
        String url = baseUrl + "/cart";

        AddOrUpdateCartItemDTO dto = new AddOrUpdateCartItemDTO(userId, bookId, quantity);

        ResponseEntity<String> response = restTemplate.exchange(
            url,
            HttpMethod.PUT,
            new HttpEntity<>(dto),
            new ParameterizedTypeReference<>() {}
        );

        return response.getBody();
    }

    private record AddOrUpdateCartItemDTO(Long userId, Long bookId, int quantity) {}

    @Override
    public String removeItemFromCart(Long userId, Long bookId) {
        String url = baseUrl + "/cart";

        RemoveCartItemDTO dto = new RemoveCartItemDTO(userId, bookId);

        ResponseEntity<String> response = restTemplate.exchange(
            url,
            HttpMethod.DELETE,
            new HttpEntity<>(dto),
            new ParameterizedTypeReference<>() {}
        );

        return response.getBody();
    }

    private record RemoveCartItemDTO(Long userId, Long bookId) {}

    @Override
    public String truncateCart(Long userId) {
        String url = baseUrl + "/cart/" + userId;

        ResponseEntity<String> response = restTemplate.exchange(
            url,
            HttpMethod.DELETE,
            null,
            new ParameterizedTypeReference<>() {}
        );

        return response.getBody();
    }
}
