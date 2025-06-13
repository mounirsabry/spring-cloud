package jets.spring_boot.service;

import java.util.List;

import jets.spring_boot.model.dto.BookDTO;

public interface BookServiceOld {

    List<BookDTO> getAllBooks();

    List<BookDTO> getAllBooks(List<Long> ids);
    
    BookDTO getBookById(long bookId);
    
    BookDTO addBook(BookDTO bookDTO);
    
    void updateBook(BookDTO bookDTO);
    
    void deleteBook(long bookId);
}
