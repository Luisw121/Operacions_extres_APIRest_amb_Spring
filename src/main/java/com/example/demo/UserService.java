package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    @Autowired
    UserDAO repository;
    public List<User> findAll() {
        return repository.findAll();
    }

    public User getUserById(Integer id){
        Optional<User> optionalUser;
        optionalUser = repository.findById(id);
        return optionalUser.orElse(null);
    }

    public User saveUser(User user) {
        return repository.save(user);
    }


    public void deleteUserById(Integer id){
        repository.deleteById(id);
    }
}