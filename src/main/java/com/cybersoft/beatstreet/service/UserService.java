package com.cybersoft.beatstreet.service;


import com.cybersoft.beatstreet.model.User;
import com.cybersoft.beatstreet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUserById(int id) {
        return userRepository.findOne(id);
    }

    public User getUserByUsername(String username) {
        List<User> users = userRepository.findAll();
        for (User u : users) {
            if (u.getName().equals(username)) {
                return u;
            }
        }
        return null;
    }


    // GETTERS AND SETTERS

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
