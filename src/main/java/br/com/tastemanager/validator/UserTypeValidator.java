package br.com.tastemanager.validator;

import br.com.tastemanager.repository.UserRepository;
import br.com.tastemanager.repository.UserTypeRepository;
import org.springframework.stereotype.Component;

@Component
public class UserTypeValidator {

    private final UserTypeRepository userTypeRepository;

    private final UserRepository userRepository;

    public UserTypeValidator(UserRepository userRepository, UserTypeRepository userTypeRepository, UserRepository userRepository1) {
        this.userTypeRepository = userTypeRepository;
        this.userRepository = userRepository1;
    }

    public void validateUserTypeName(String userTypeName) {
        if (userTypeRepository.findByName(userTypeName).isPresent()) {
            throw new IllegalArgumentException("UserType with this name already exists. Please choose a different one.");
        }
    }

    public void validateUserTypeId(Long userTypeId) {
        if (!userTypeRepository.findById(userTypeId).isPresent()) {
            throw new IllegalArgumentException("UserType with this id do not exists. Please choose a different one.");
        }
    }

    public void validateUserTypeIsInUse(Long id) {
        Long count = userRepository.countByUserTypeId(id);
        if (count > 0) {
            throw new IllegalArgumentException("Cannot delete UserType with associated users");
        }
    }

}
