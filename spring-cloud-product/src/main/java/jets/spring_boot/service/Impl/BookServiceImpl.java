package jets.spring_boot.service.Impl;

import jets.spring_boot.model.dto.BookDTO;
import jets.spring_boot.model.entities.Book;
import jets.spring_boot.exceptions.ResourceNotFoundException;
import jets.spring_boot.model.mappers.BookMapper;
import jets.spring_boot.repo.BookRepo;
import jets.spring_boot.service.BookService;
import jets.spring_boot.utils.DataValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepo bookRepo, BookMapper bookMapper) {
        this.bookRepo = bookRepo;
        this.bookMapper = bookMapper;
    }
    
    private void validateBook(BookDTO bookDTO) {
        DataValidator.validateTitle(bookDTO.getTitle());
    }
    
    public List<BookDTO> getAllBooks() {
        return bookRepo.findAll().stream()
                .map(bookMapper::entityToDto)
                .toList();
    }

    public List<BookDTO> getAllBooks(List<Long> ids) {
        List<Book> books = new ArrayList<>();
        ids.forEach(id -> {
            if (id == null) {
                books.add(null);
                return;
            }
            books.add(bookRepo.findBookByBookId(id));
        });

        return books.stream()
                .map(bookMapper::entityToDto)
                .toList();
    }
    
    public BookDTO getBookById(long id) {
        DataValidator.validateId(id);

        Book foundBook = bookRepo.findBookByBookId(id);
        if (foundBook == null) {
            throw new ResourceNotFoundException("Book with id " + id + " not found");
        }

        return bookMapper.entityToDto(foundBook);
    }

    @Transactional
    public BookDTO addBook(BookDTO bookDTO) {
        validateBook(bookDTO);

        Book addedBook = bookRepo.save(bookMapper.dtoToEntity(bookDTO));

        bookDTO.setBookId(addedBook.getBookId());
        return bookDTO;
    }

    @Transactional
    public void updateBook(BookDTO bookDTO) {
        validateBook(bookDTO);
        DataValidator.validateId(bookDTO.getBookId());

        Book foundBook = bookRepo.findBookByBookId(bookDTO.getBookId());
        if (foundBook == null) {
            throw new ResourceNotFoundException("Book with id " + bookDTO.getBookId() + " not found");
        }
        
        bookRepo.save(bookMapper.dtoToEntity(bookDTO));
    }

    @Transactional
    public void deleteBook(long id) {
        DataValidator.validateId(id);

        Book foundBook = bookRepo.findBookByBookId(id);
        if (foundBook == null) {
            throw new ResourceNotFoundException("Book with id " + id + " not found");
        }

        bookRepo.deleteByBookId(id);
    }
}
