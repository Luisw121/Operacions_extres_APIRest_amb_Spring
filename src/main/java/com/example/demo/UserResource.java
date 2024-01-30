package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS = "/users";
    @Autowired
    UserController userController;

    @GetMapping
    public List<UserDto> readAll(){
        return userController.getAllUsers();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Integer id, @RequestBody UserDto userDto) {
        UserDto updatedUserDto = userController.updateUser(id, userDto);
        if (updatedUserDto != null) {
            return ResponseEntity.ok(updatedUserDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userController.deleteUser(id);
    }
    //ultimos cambios
    @PatchMapping("{id}")
    public UserDto updateUserPatch(@PathVariable Integer id, @RequestBody JsonPatch patch, @RequestBody UserDto userDto) {
        UserDto user = userController.updateUser(id, userDto);
        if (user == null) {
            throw new IllegalArgumentException("User not found with id " + id);
        }
        try {
            // Aplicar el parche al usuario existente
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(user, JsonNode.class));
            User patchedUser = objectMapper.treeToValue(patched, User.class);
            patchedUser.setId(id);
            // Guardar el usuario actualizado
            User updatedUser = userController.updateUser(patchedUser);
            return new UserDto(updatedUser);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user with id " + id, e);
        }
    }

}
