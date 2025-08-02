package br.com.tastemanager.validator;

import br.com.tastemanager.repository.RestaurantRepository;
import br.com.tastemanager.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    private final UserRepository userRepository;

    private final RestaurantRepository restaurantRepository;

    public UserValidator(UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public void validateLoginAvailability(String login) {
        if (userRepository.findIdByLogin(login).isPresent()) {
            throw new IllegalArgumentException("This login is unavailable. Please choose a different one.");
        }
    }

    public void validateUserExistsById(Long id) {
        if (!userRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("User with the given ID does not exist.");
        }
    }
    public void validateUserIsAOwnerInUseById(Long id){
        if (restaurantRepository.existsByOwnerId(id)) {
            throw new IllegalArgumentException("Cannot delete: the user is the owner of a restaurant.");
        }
    }
}
