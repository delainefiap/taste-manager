package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.ChangePasswordRequest;
import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.entity.User;
import br.com.tastemanager.mapper.UserMapper;
import br.com.tastemanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordService passwordService;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordService = passwordService;
    }

    public String createUser(UserRequestDTO userRequest) {
        var user = userMapper.UserRequestDtoToEntity(userRequest);

        if (userRepository.findIdByLogin(user.getLogin()).isPresent()) {
            throw new IllegalArgumentException("This login is unavailable. Please choose a different one.");
        }

        userRepository.save(user);
        return "User created successfully";
    }

    public String updateUser(Long id, UserUpdateRequestDTO userRequest) {
        userRepository.updateUser(id, userMapper.UserUpdateRequestDtoToEntity( userRequest));
        return "User updated successfully";
    }

    public String deleteUser(String login) {
        userRepository.deleteUser(login);
        return "User deleted successfully";
    }

    public void updatePassword(ChangePasswordRequest changePasswordRequest) {

        if (passwordIsValidForUser(changePasswordRequest.getLogin(), changePasswordRequest.getOldPassword())) {

            userRepository.updatePassword(changePasswordRequest.getLogin(), changePasswordRequest.getPassword());
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

    private boolean passwordIsValidForUser(String login, String password) {
        return userRepository.findUserByLogin(login).map(user -> user.getPassword().equals(password)).orElseThrow(() -> new IllegalArgumentException("Password incorrect"));
    }


}
