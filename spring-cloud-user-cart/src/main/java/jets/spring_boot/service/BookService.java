package jets.spring_boot.service;

import java.util.ArrayList;
import java.util.List;

import jets.spring_boot.model.dto.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "spring-cloud-product")
public interface BookService {

    @GetMapping("/books")
    List<BookDTO> getAllBooks();

    default List<BookDTO> getAllBooks(@RequestBody List<Long> ids) {
        List<BookDTO> list = new ArrayList<>();
        BookDTO dto;
        
        for (Long id : ids) {
            dto = this.getBookById(id);
            if (dto != null) {
                list.add(dto);
            }
        }
        
        return list;
    }
    
    @GetMapping("/books/{id}")
    BookDTO getBookById(@PathVariable("id") long bookId);
    
    @PostMapping("/books")
    BookDTO addBook(@RequestBody BookDTO bookDTO);
    
    @PutMapping("/books")
    void updateBook(@RequestBody BookDTO bookDTO);
    
    @DeleteMapping("/books")
    void deleteBook(long bookId);
}