package jets.spring_boot;

import jets.spring_boot.model.dto.BookDTO;
import jets.spring_boot.model.entities.CartItem;
import jets.spring_boot.model.entities.User;
import jets.spring_boot.repo.CartItemRepo;
import jets.spring_boot.repo.UserRepo;
import jets.spring_boot.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	CommandLineRunner insertTestDataRunner(
			UserRepo userRepo,
			BookService bookService,
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

			List<Long> ids = new ArrayList<>();
			ids.add(1L);
			ids.add(2L);

			List<BookDTO> books = bookService.getAllBooks(ids);

			CartItem item1 = new CartItem();
			item1.setUserId(user1.getUserId());
			item1.setBookId(books.getFirst().getBookId());
			item1.setQuantity(1);
			CartItem savedCartItem1 = cartItemRepo.save(item1);
			if (savedCartItem1 == null) {
				throw new IllegalStateException("Could not save cart item 1");
			}

			CartItem item2 = new CartItem();
			item2.setUserId(user1.getUserId());
			item2.setBookId(books.get(1).getBookId());
			item2.setQuantity(2);
			CartItem savedCartItem2 = cartItemRepo.save(item2);
			if (savedCartItem2 == null) {
				throw new IllegalStateException("Could not save cart item 2");
			}

			// Printing testing data for debugging.
			List<User> users = userRepo.findAll();
			books = bookService.getAllBooks();
			List<CartItem> cartItems = cartItemRepo.findAllByUserId(user1.getUserId());

			System.out.println("Current Testing data");
			users.forEach(System.out::println);
			books.forEach(System.out::println);
			cartItems.forEach(System.out::println);
			System.out.println("End of testing data section");
		};
	}
}
