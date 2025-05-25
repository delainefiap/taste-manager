package br.com.tastemanager.controller;

import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.entity.User;
import br.com.tastemanager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//TODO melhorar o nome
@RequestMapping("/user")
public class Controller {

    private final UserService userService;

    public Controller(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Realiza a criação de um usuário.")
    @PostMapping("/create")
    public ResponseEntity<String> createUser(
            @RequestBody User userRequest
    ){
        var user = this.userService.createUser(userRequest);

        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Realiza a atualização de um usuário.")
    @GetMapping("/update")
    public ResponseEntity<String> updateUser(
            @RequestBody User userRequest,
            Long id
    ){
        var user = this.userService.updateUser(userRequest, id);

        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Realiza a exclusão de um usuário.")
    @GetMapping("/delete")
    public ResponseEntity<String> deleteUser(
            Long id
    ){
        var user = this.userService.deleteUser(id);

        return ResponseEntity.ok(user);

    }




}
