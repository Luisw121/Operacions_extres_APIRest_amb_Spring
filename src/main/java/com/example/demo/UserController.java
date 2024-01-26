package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    public List<UserDto> getAllUsers(){
        List<User> users = userService.findAll();
        //List<UserDto> userDtos = users.stream().map(UserDto::new).toList();
        List<UserDto> userDtos = users.stream().map(user -> new UserDto(user)).toList();
        return userDtos;
    }

    public UserDto getUserById(Integer id){
        UserDto userDto;
        User user = userService.getUserById(id);
        return new UserDto(user);
    }
    public User saveUser(User user){
        return userService.saveUser(user);
    }

    public User updateUser(User user) {
        User existingUser = userService.getUserById(user.getId());
        if (existingUser != null) {
            existingUser.setEmail(user.getEmail());
            existingUser.setFullName(user.getFullName());
            existingUser.setPassword(user.getPassword());
            return userService.saveUser(existingUser);
        } else {
            throw new IllegalArgumentException("User not found with id " + user.getId());
        }
    }

    public void deleteUser(Integer id){
        userService.deleteUserById(id);
    }
/*
    public UserDto getUserById() {
        return null;
    }

 */
}