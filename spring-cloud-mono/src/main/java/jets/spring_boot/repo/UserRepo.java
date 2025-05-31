package jets.spring_boot.repo;

import java.util.List;

import jets.spring_boot.model.entities.User;
import org.springframework.data.repository.Repository;

public interface UserRepo extends Repository<User, Long> {
    
    List<User> findAll();
    
    User findUserByUserId(long userId);
    
    User save(User user);
    
    void deleteByUserId(long id);

    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email, String password);
}
