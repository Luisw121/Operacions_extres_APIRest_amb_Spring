package com.example.demo;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserDto{
    private Integer id;
    private String email;
    private String fullName;
    private String password;
    public UserDto(Integer id, String email, String fullname, String password) {
        this.id = id;
        this.email = email;
        this.fullName = fullname;
        this.password = password;
    }
    public UserDto(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.password = user.getPassword();
    }
}