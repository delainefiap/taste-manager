package br.com.tastemanager.controller.openapi;

import br.com.tastemanager.dto.request.ChangePasswordRequestDTO;
import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.dto.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface UserControllerDocs {

    @Operation(summary = "Realiza a criação de um usuário.")
    @PostMapping("/create")
    ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequest);

    @Operation(summary = "Realiza a atualização de um usuário.")
    @PatchMapping("/update/{id}")
    ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequestDTO userRequest);

    @Operation(summary = "Realiza a exclusão de um usuário.")
    @DeleteMapping("/delete")
    ResponseEntity<String> deleteUser(@RequestParam Long id);

    @Operation(summary = "Troca a senha do usuário.")
    @PostMapping("/change-password/{id}")
    ResponseEntity<String> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO);

    @Operation(summary = "Valida o login do usuário.")
    @PostMapping("/validate-login")
    ResponseEntity<String> validateLogin(@RequestParam String login, @RequestParam String password);

    @Operation(summary = "Pesquisa todos os usuários cadastrados.")
    @GetMapping("/find-all")
    ResponseEntity<?> findAllUsers(@RequestParam int page, @RequestParam int size);
}