package jets.spring_boot.rest;

import java.util.List;

import jakarta.annotation.Nullable;
import jets.spring_boot.model.dto.BookDTO;
import jets.spring_boot.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/books",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final BookService bookService;

    private BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    ResponseEntity<List<BookDTO>> getAll(@Nullable @RequestBody List<Long> ids) {
        if (ids == null) {
            return ResponseEntity.ok(bookService.getAllBooks());
        }

        return ResponseEntity.ok(bookService.getAllBooks(ids));
    }
    
    @GetMapping("/{bookId}")
    ResponseEntity<BookDTO> getById(@PathVariable("bookId") Long bookId) {
        if (bookId == null) {
            throw new IllegalArgumentException("Book bookId cannot be null");
        }

        BookDTO dto = bookService.getBookById(bookId);
        return ResponseEntity.ok(dto);
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BookDTO> add(@RequestBody BookDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Book dto cannot be null");
        }

        BookDTO insertedDTO = bookService.addBook(dto);
        return ResponseEntity.ok(insertedDTO);
    }
    
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> update(@RequestBody BookDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Book dto cannot be null");
        }
        if (dto.getBookId() == null) {
            throw new IllegalArgumentException("Book bookId cannot be null");
        }

        bookService.updateBook(dto);
        return ResponseEntity.ok("Book was updated successfully");
    }
    
    @DeleteMapping("/{bookId}")
    ResponseEntity<String> delete(@PathVariable("bookId") Long bookId) {
        if (bookId == null) {
            throw new IllegalArgumentException("Book bookId cannot be null");
        }

        bookService.deleteBook(bookId);
        return ResponseEntity.ok("Book was deleted successfully");
    }
}
