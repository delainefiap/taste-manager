package br.com.tastemanager.controller;

import br.com.tastemanager.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.dto.response.UserTypeResponseDTO;
import br.com.tastemanager.service.UserTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user-type")
public class UserTypeController {
    private final UserTypeService userTypeService;

    public UserTypeController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserTypeResponseDTO> createUserType(@RequestBody UserTypeRequestDTO userTypeRequestDTO) {
        UserTypeResponseDTO response = userTypeService.createUserType(userTypeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAllUserTypes(@RequestParam int page, @RequestParam int size) {
        var response = userTypeService.findAllUserTypes(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<UserTypeResponseDTO> updateUserType(@PathVariable Long id, @RequestBody UserTypeRequestDTO userTypeRequestDTO) {
        UserTypeResponseDTO response = userTypeService.updateUserType(id, userTypeRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserType(@PathVariable Long id) {
        var response = userTypeService.deleteUserType(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<UserTypeResponseDTO> findUserTypeById(@PathVariable Long id) {
        UserTypeResponseDTO response = userTypeService.findUserTypeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}