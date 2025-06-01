package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.ChangePasswordRequest;
import br.com.tastemanager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PasswordService {

    private final UserRepository userRepository;

    public PasswordService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isPasswordValid(String login, String password) {
        return userRepository.findUserByLogin(login)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

    public void validatePasswordRequest(ChangePasswordRequest request) {
        if (!StringUtils.hasText(request.getPassword())) {
            throw new IllegalArgumentException("Password cannot be null, empty, or blank.");
        }
    }

    public void validateOldPassword(boolean isValid) {
        if (!isValid) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
    }
}