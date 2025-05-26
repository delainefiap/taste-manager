package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.ChangePasswordRequest;
import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.entity.User;
import br.com.tastemanager.mapper.UserMapper;
import br.com.tastemanager.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public String createUser(User userRequest) {

//        var user = userMapper.toEntity(userRequestDTO);

        userRepository.createUser(userRequest);

        return "User created successfully";
    }

    public String updateUser(User userRequest, Long id) {

        userRepository.updateUser(userRequest, id);

        return "User updated successfully";
    }

    public String deleteUser(Long id) {

        userRepository.deleteUser(id);

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

    private boolean passwordIsValidForUser(Long id, String oldPassword) {
        return userRepository.findById(id)
                .map(user -> user.getPassword().equals(oldPassword))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }



}
