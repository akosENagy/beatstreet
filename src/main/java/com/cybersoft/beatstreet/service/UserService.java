package com.cybersoft.beatstreet.service;


import com.cybersoft.beatstreet.model.User;
import com.cybersoft.beatstreet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    // GETTERS AND SETTERS

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
