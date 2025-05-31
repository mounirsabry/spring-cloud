package jets.spring_boot;

import jets.spring_boot.model.entities.Book;
import jets.spring_boot.model.entities.CartItem;
import jets.spring_boot.model.entities.User;
import jets.spring_boot.repo.BookRepo;
import jets.spring_boot.repo.CartItemRepo;
import jets.spring_boot.repo.UserRepo;
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
	CommandLineRunner insertTestDataRunner(
			UserRepo userRepo,
			BookRepo bookRepo,
			CartItemRepo cartItemRepo) {
		return args -> {

			User user1 = new User();
			user1.setName("User1");
			user1.setEmail("user1@email.com");
			user1.setPassword("user1abc");
			User savedUser = userRepo.save(user1);
			if (savedUser == null) {
				throw new IllegalStateException("Could not save user1");
			}

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

			CartItem item1 = new CartItem();
			item1.setUserId(user1.getUserId());
			item1.setBookId(book1.getBookId());
			item1.setQuantity(1);
			CartItem savedCartItem1 = cartItemRepo.save(item1);
			if (savedCartItem1 == null) {
				throw new IllegalStateException("Could not save cart item 1");
			}

			CartItem item2 = new CartItem();
			item2.setUserId(user1.getUserId());
			item2.setBookId(book2.getBookId());
			item2.setQuantity(2);
			CartItem savedCartItem2 = cartItemRepo.save(item2);
			if (savedCartItem2 == null) {
				throw new IllegalStateException("Could not save cart item 2");
			}

			// Printing testing data for debugging.
			List<User> users = userRepo.findAll();
			List<Book> books = bookRepo.findAll();
			List<CartItem> cartItems = cartItemRepo.findAllByUserId(user1.getUserId());

			System.out.println("Current Testing data");
			users.forEach(System.out::println);
			books.forEach(System.out::println);
			cartItems.forEach(System.out::println);
			System.out.println("End of testing data section");
		};
	}
}
