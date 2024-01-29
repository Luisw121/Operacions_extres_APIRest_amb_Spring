package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Users")
public class User {
    @Id
    private Integer id;
    private String email;
    @Column(name = "full_name")
    private String fullName;
    private String password;

    public User(){

    }
    public User(Integer id, String email, String full_name, String password) {
        this.id = id;
        this.email = email;
        this.fullName = full_name;
        this.password = password;
    }

    public User(UserDto userDto){
        this.id = userDto.getId();
        this.email = userDto.getEmail();
        this.fullName = userDto.getFullName();
        this.password = userDto.getPassword();
    }


}