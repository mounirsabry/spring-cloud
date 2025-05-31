package jets.spring_boot.client;

import jets.spring_boot.model.dto.BookDTO;
import jets.spring_boot.service.BookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BookServiceProxy implements BookService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public BookServiceProxy(RestTemplate restTemplate, @Value("${product.service.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        String url = baseUrl + "/books";

        ResponseEntity<List<BookDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @Override
    public List<BookDTO> getAllBooks(List<Long> ids) {
        String url = baseUrl + "/books";

        ResponseEntity<List<BookDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(ids),
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @Override
    public BookDTO getBookById(long bookId) {
        String url = baseUrl + "/books/" + bookId;

        return restTemplate.getForObject(url, BookDTO.class);
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        String url = baseUrl + "/books";

        AddBookDTO addBookDTO = new AddBookDTO(bookDTO.getTitle());
        return restTemplate.postForObject(url, addBookDTO, BookDTO.class);
    }

    private record AddBookDTO(String title) {}

    @Override
    public void updateBook(BookDTO bookDTO) {
        String url = baseUrl + "/books";

        restTemplate.put(url, bookDTO);
    }

    @Override
    public void deleteBook(long bookId) {
        String url = baseUrl + "/books/" + bookId;

        restTemplate.delete(url);
    }
}
