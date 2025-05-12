package com.org.test.subscription.controller;

import com.org.test.subscription.dto.UserDTO;
import com.org.test.subscription.dto.UserResponseDTO;
import com.org.test.subscription.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Users API")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Operation(summary = "Получить пользователя", description = "Получить пользователя по его id")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable @Min(1) Long id) {
        var user = usersService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Добавить пользователя", description = "Добавить пользователя и получить информацию о нем")
    @PostMapping
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody UserDTO user) {
        return ResponseEntity.ok(usersService.addUser(user));
    }

    @Operation(summary = "Обновить пользователя", description = "Обновить информацию о пользователе")
    @PutMapping("/{id}")
    public void updateUser(@PathVariable @Min(1) Long id, @RequestBody UserDTO user) {
        usersService.updateUser(id, user);
    }

    @Operation(summary = "Удалить пользователя", description = "Удалить пользователя по его id")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable @Min(1) Long id) {
        usersService.deleteUser(id);
    }

}
