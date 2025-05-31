package jets.spring_boot.service.Impl;

import jets.spring_boot.model.dto.UserDTO;
import jets.spring_boot.model.entities.User;
import jets.spring_boot.exceptions.ResourceNotFoundException;
import jets.spring_boot.exceptions.UnknownException;
import jets.spring_boot.model.mappers.UserMapper;
import jets.spring_boot.repo.UserRepo;
import jets.spring_boot.service.UserService;
import jets.spring_boot.utils.DataValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepo userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }
    
    private void validateUser(UserDTO userDTO) {
        DataValidator.validateName(userDTO.getName());
        DataValidator.validateEmail(userDTO.getEmail());
    }
    
    private void validateLoginCredentials(String email, String password) {
        DataValidator.validateEmail(email);
        DataValidator.validatePassword(password, null);
    }
    
    private void validatePasswords(String currentPassword, String newPassword) {
        DataValidator.validatePassword(currentPassword, "currentPassword");
        DataValidator.validatePassword(newPassword, "newPassword");
    }

    @Override
    public List<UserDTO> getAllUsers() {

        return userRepo.findAll().stream()
                .map(userMapper::entityToDto)
                .toList();
    }

    @Override
    public UserDTO getUserById(long userId) {
        DataValidator.validateId(userId);

        User foundUser = userRepo.findUserByUserId(userId);
        if (foundUser == null) {
            throw new ResourceNotFoundException("User with id " + userId + " not found");
        }

        System.out.println(foundUser);
        return userMapper.entityToDto(foundUser);
    }

    @Override
    @Transactional
    public void updateUser(UserDTO userDTO) {
        validateUser(userDTO);
        DataValidator.validateId(userDTO.getUserId());

        User foundUser = userRepo.findUserByUserId(userDTO.getUserId());
        if (foundUser == null) {
            throw new ResourceNotFoundException("User with id " + userDTO.getUserId() + " not found");
        }

        User entity = userMapper.dtoToEntity(userDTO);

        // Only the password is not allowed to be updated by the user by this function.
        entity.setPassword(foundUser.getPassword());

        userRepo.save(entity);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        DataValidator.validateId(id);

        User foundUser = userRepo.findUserByUserId(id);
        if (foundUser == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
        
        userRepo.deleteByUserId(id);
    }

    @Override
    public UserDTO login(String email, String password) {
        validateLoginCredentials(email, password);

        User loggedUser = userRepo.findUserByEmailAndPassword(email, password);
        if (loggedUser == null) {
            throw new ResourceNotFoundException("Email or password is incorrect");
        }

        return userMapper.entityToDto(loggedUser);
    }

    @Override
    public void register(UserDTO userDTO, String password) {
        validateUser(userDTO);
        DataValidator.validatePassword(password, null);

        User userWithSameEmail = userRepo.findUserByEmail(userDTO.getEmail());
        if (userWithSameEmail != null) {
            throw new IllegalArgumentException("User with email " + userDTO.getEmail() + " already exists");
        }

        User sentUser = userMapper.dtoToEntity(userDTO);
        sentUser.setPassword(password);

        User registeredUser = userRepo.save(sentUser);
        if (registeredUser == null) {
            throw new UnknownException("Could not register the user");
        }
    }

    @Override
    @Transactional
    public void changeEmail(long id, String newEmail) {
        DataValidator.validateId(id);
        DataValidator.validateEmail(newEmail);

        User foundUser = userRepo.findUserByUserId(id);
        if (foundUser == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }

        if (Objects.equals(foundUser.getEmail(), newEmail)) {
            throw new IllegalArgumentException("New email cannot be the same as the current one");
        }

        foundUser.setEmail(newEmail);
        userRepo.save(foundUser);
    }

    @Override
    @Transactional
    public void changePassword(long id, String currentPassword, String newPassword) {
        DataValidator.validateId(id);
        validatePasswords(currentPassword, newPassword);

        User foundUser = userRepo.findUserByUserId(id);
        if (foundUser == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }

        if (!Objects.equals(foundUser.getPassword(), currentPassword)) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (Objects.equals(foundUser.getPassword(), newPassword)) {
            throw new IllegalArgumentException("New password cannot be the same as the current one");
        }

        foundUser.setPassword(newPassword);
        userRepo.save(foundUser);
    }
}
