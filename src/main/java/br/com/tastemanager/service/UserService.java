package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.ChangePasswordRequest;
import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.entity.User;
import br.com.tastemanager.mapper.UserMapper;
import br.com.tastemanager.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public String createUser(UserRequestDTO userRequest) {

        var user = userMapper.toEntity(userRequest);

        try {
            userRepository.save(user);
            return "User created successfully";
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("O login informado já está em uso. Por favor, escolha outro.");
        }
    }

    public String updateUser(UserRequestDTO userRequest) {

        var user = userMapper.toEntity(userRequest);

        userRepository.updateUser(user);

        return "User updated successfully";
    }

    public String deleteUser(String login) {

        userRepository.deleteUser(login);

        return "User deleted successfully";
    }

    public void updatePassword(ChangePasswordRequest changePasswordRequest) {
        // TODO implementar a lógica de validação da senha antiga
        if(passwordIsValidForUser(changePasswordRequest.getId(), changePasswordRequest.getOldPassword())) {
            userRepository.updatePassword(changePasswordRequest.getId(), changePasswordRequest.getPassword());
        } else {
            throw new IllegalArgumentException("Old password is incorrect");
        }
    }

    public boolean validateLogin(String login, String password) {
        return userRepository.findAll(1, 0).stream()
                .anyMatch(user -> user.getLogin().equals(login) && user.getPassword().equals(password));
    }

    private boolean passwordIsValidForUser(Long id, String oldPassword) {
        return userRepository.findById(id)
                .map(user -> user.getPassword().equals(oldPassword))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }



}
