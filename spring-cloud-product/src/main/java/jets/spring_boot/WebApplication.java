package jets.spring_boot;

import jets.spring_boot.model.entities.Book;
import jets.spring_boot.repo.BookRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Bean
	CommandLineRunner insertTestDataRunner(BookRepo bookRepo) {
		return args -> {

			Book book1 = new Book();
			book1.setTitle("Book 1");
			Book savedBook1 = bookRepo.save(book1);
			if (savedBook1 == null) {
				throw new IllegalStateException("Could not save book1");
			}

			Book book2 = new Book();
			book2.setTitle("Book 2");
			Book savedBook2 = bookRepo.save(book2);
			if (savedBook2 == null) {
				throw new IllegalStateException("Could not save book2");
			}

			Book book3 = new Book();
			book3.setTitle("Book 3");
			Book savedBook3 = bookRepo.save(book3);
			if (savedBook3 == null) {
				throw new IllegalStateException("Could not save book3");
			}

			Book book4 = new Book();
			book4.setTitle("Book 4");
			Book savedBook4 = bookRepo.save(book4);
			if (savedBook4 == null) {
				throw new IllegalStateException("Could not save book4");
			}

			List<Book> books = bookRepo.findAll();

			System.out.println("Current Testing data");
			books.forEach(System.out::println);
			System.out.println("End of testing data section");
		};
	}
}
