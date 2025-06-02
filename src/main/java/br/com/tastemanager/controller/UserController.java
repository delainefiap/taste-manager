package br.com.tastemanager.controller;

import br.com.tastemanager.dto.request.ChangePasswordRequest;
import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.dto.response.UserResponseDTO;
import br.com.tastemanager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Realiza a criação de um usuário.")
    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequest) {
        var response = this.userService.createUser(userRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Realiza a atualização de um usuário.")
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,
                                             @RequestBody UserUpdateRequestDTO userRequest) {

        var response = this.userService.updateUser(id, userRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Realiza a exclusão de um usuário.")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam Long id) {
        var response = this.userService.deleteUser(id);
        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Troca a senha do usuário.")
    @PostMapping("/change-password/{id}")
    public ResponseEntity<String> changePassword(@PathVariable Long id,
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        this.userService.updatePassword(id, changePasswordRequest);
        return ResponseEntity.ok("Password changed successfully.");
    }

    @Operation(summary = "Valida o login do usuário.")
    @PostMapping("/validate-login")
    public ResponseEntity<String> validateLogin(@RequestParam String login, @RequestParam String password) {
        boolean isValid = userService.validateLogin(login, password);
        return isValid ? ResponseEntity.ok("Login successful")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @Operation(summary = "Pesquisa todos os usuários cadastrados.")
    @GetMapping("/find-all")
    public ResponseEntity<?> findAllUsers(@RequestParam int page, @RequestParam int size) {
        var users = userService.findAllUsers(page, size);
        return ResponseEntity.ok(users);
    }

}
