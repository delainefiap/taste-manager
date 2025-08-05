package br.com.tastemanager.controller.openapi;

import br.com.tastemanager.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.dto.response.UserTypeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface UserTypeControllerDocs {

    @Operation(summary = "Cria um novo tipo de usuário.")
    @PostMapping("/create")
    ResponseEntity<UserTypeResponseDTO> createUserType(@RequestBody UserTypeRequestDTO userTypeRequestDTO);

    @Operation(summary = "Busca todos os tipos de usuários.")
    @GetMapping("/find-all")
    ResponseEntity<?> findAllUserTypes(@RequestParam int page, @RequestParam int size);

    @Operation(summary = "Atualiza um tipo de usuário pelo ID.")
    @PatchMapping("/update/{id}")
    ResponseEntity<UserTypeResponseDTO> updateUserType(@PathVariable Long id, @RequestBody UserTypeRequestDTO userTypeRequestDTO);

    @Operation(summary = "Exclui um tipo de usuário pelo ID.")
    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteUserType(@PathVariable Long id);

    @Operation(summary = "Busca um tipo de usuário pelo ID.")
    @GetMapping("/find-by-id/{id}")
    ResponseEntity<UserTypeResponseDTO> findUserTypeById(@PathVariable Long id);
}