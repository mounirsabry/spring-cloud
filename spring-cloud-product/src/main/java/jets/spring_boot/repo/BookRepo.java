package jets.spring_boot.repo;

import java.util.List;

import jets.spring_boot.model.entities.Book;
import org.springframework.data.repository.Repository;

public interface BookRepo extends Repository<Book, Long> {
    
    List<Book> findAll();

    Book findBookByBookId(long bookId);

    Book save(Book book);
    
    void deleteByBookId(long id);
}
