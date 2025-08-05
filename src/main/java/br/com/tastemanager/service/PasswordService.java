package br.com.tastemanager.service;

import br.com.tastemanager.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final UserRepository userRepository;

    public PasswordService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isPasswordValid(Long id, String password) {
        return userRepository.findById(id)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

    public void validateOldPassword(boolean isValid) {
        if (!isValid) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
    }
}