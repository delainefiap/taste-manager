package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.ChangePasswordRequest;
import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.dto.response.UserResponseDTO;
import br.com.tastemanager.entity.User;
import br.com.tastemanager.mapper.UserMapper;
import br.com.tastemanager.repository.UserRepository;
import br.com.tastemanager.validator.UserValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordService passwordService;
    private final UserValidator userValidation;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordService passwordService, UserValidator userValidation) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordService = passwordService;
        this.userValidation = userValidation;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequest) {
        userValidation.validateLoginAvailability(userRequest.getLogin());
        User user = userMapper.UserRequestDtoToEntity(userRequest);
        userRepository.save(user);
        return userMapper.userToUserResponseDto(user);

    }

    public String updateUser(Long id, UserUpdateRequestDTO userRequest) {
        userValidation.validateUserExistsById(id);
        if (userRequest.getName() != null && (userRequest.getName().isEmpty() || userRequest.getName().isBlank())){
            throw new IllegalArgumentException("Name cannot be blank or empty.");
        }
        if (userRequest.getEmail() != null && (userRequest.getEmail().isEmpty() || userRequest.getEmail().isBlank() || !userRequest.getEmail().contains("@"))) {
            throw new IllegalArgumentException("E-mail cannot be blank or empty.");
        }
        userRepository.updateUser(id, userMapper.userUpdateRequestDtoToEntity(userRequest));
        return "User updated successfully";
    }

    public String deleteUser(Long id) {
        userValidation.validateUserExistsById(id);
        userRepository.deleteUser(id);
        return "User deleted successfully";
    }

    public void updatePassword(Long id, ChangePasswordRequest changePasswordRequest) {
        userValidation.validateUserExistsById(id);

        if (passwordIsValidForUser(id, changePasswordRequest.getOldPassword())) {

            userRepository.updatePassword(id, changePasswordRequest.getNewPassword());
        } else {
            throw new IllegalArgumentException("Old password is incorrect");
        }
    }

    public boolean validateLogin(String login, String password) {
        return userRepository.findAll(1, 0).stream().anyMatch(user -> user.getLogin().equals(login) && user.getPassword().equals(password));
    }

    public List<User> findAllUsers(int page, int size) {
        int offset = (page - 1) * size;
        return userRepository.findAll(size, offset);
    }

    private boolean passwordIsValidForUser(Long id, String password) {
        return userRepository.findById(id).map(user -> user.getPassword().equals(password)).orElseThrow(() -> new IllegalArgumentException("Password incorrect"));
    }


}
