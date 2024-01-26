package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS = "/users";
    @Autowired
    UserController userController;

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = new User(userDto);
        User savedUser = userController.saveUser(user);
        return new UserDto(savedUser);
    }

    /*
    {
    "id":1,
    "email":"ejempo@ejemplo.com",
    "fullname":"usuario",
    "password":"password"
}
     */

    @GetMapping
    public List<UserDto> readAll(){
        return userController.getAllUsers();
    }

    @GetMapping("{id}")
    public UserDto getUser(@PathVariable Integer id){
        return userController.getUserById(id);
    }

    @PutMapping("{id}")
    public UserDto replaceUser(@PathVariable Integer id, @RequestBody UserDto userDto){
        User updatedUser = userController.updateUser(new User(userDto));
        return new UserDto(updatedUser);
    }

    @PatchMapping("{id}")
    public UserDto updateUserPatch(@PathVariable Integer id, @RequestBody JsonPatch patch) {
        UserDto user = userController.getUserById(id);
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

    /*
     [
       { "op": "replace", "path": "/email", "value": "new-email@example.com" }
     ]
     */
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Integer id){
        userController.deleteUser(id);
    }

}
