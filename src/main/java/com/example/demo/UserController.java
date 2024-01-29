package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    public List<UserDto> getAllUsers(){
        List<User> users = userService.findAll();
        List<UserDto> userDtos = users.stream().map(user -> new UserDto(user)).toList();
        return userDtos;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        User newUser = new User(userDto);
        User savedUser = userService.save(newUser);
        UserDto savedUserDto = new UserDto(savedUser);
        return ResponseEntity.ok(savedUserDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}")
    public UserDto updateUser(Integer id, UserDto userDto) {
        User user = userService.findById(id);
        if (user == null) {
            return null;
        }
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setPassword(userDto.getPassword());
        userService.save(user);
        return new UserDto(user);
    }

}
