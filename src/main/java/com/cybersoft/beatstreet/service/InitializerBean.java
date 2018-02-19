package com.cybersoft.beatstreet.service;

import com.cybersoft.beatstreet.model.Beat;
import com.cybersoft.beatstreet.model.User;
import org.springframework.stereotype.Component;


@Component
public class InitializerBean {

    public InitializerBean(BeatService beatService, UserService userService) {

        User user = new User();
        user.setName("Admin");
        user.setProfileImagePath("noimage");

        Beat sampleBeat = new Beat("/songs/belabar_sunny9.mp3",257, "Sunny9", 500, "Sample", user);
        user.addBeat(sampleBeat);

        userService.saveUser(user);
    }
}
