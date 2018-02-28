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

        Beat sampleBeat = new Beat("/songs/belabar_sunny9.mp3",257, "Sunny9", 500, "Chill", user);
        Beat sampleBeat2 = new Beat("/songs/canton_benbient.mp3",462, "Benbient", 500, "Ambient", user);
        Beat sampleBeat3 = new Beat("/songs/peppy_y=mx+b.mp3",202, "Y=Mx+B", 500, "Chill", user);
        Beat sampleBeat4 = new Beat("/songs/seedai_2d.mp3",333, "2D", 500, "Chill", user);
        Beat sampleBeat5 = new Beat("/songs/steve_counting-sheep.mp3",222, "Counting Sheep", 500, "Ambient", user);
        user.addBeat(sampleBeat);
        user.addBeat(sampleBeat2);
        user.addBeat(sampleBeat3);
        user.addBeat(sampleBeat4);
        user.addBeat(sampleBeat5);

        userService.saveUser(user);
    }
}
