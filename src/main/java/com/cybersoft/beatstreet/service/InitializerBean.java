package com.cybersoft.beatstreet.service;

import com.cybersoft.beatstreet.model.Beat;
import com.cybersoft.beatstreet.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class InitializerBean {

    @Autowired
    public InitializerBean(BeatService beatService, UserService userService, FileHandlerService fileHandlerService) {

        User user = new User();
        user.setName("Admin");
        user.setProfileImagePath("noimage");
        userService.saveUser(user);

        Map<String, String> songMetaData;
        String[] songPaths = {"src/main/resources/public/songs/belabar_sunny9.mp3",
                              "src/main/resources/public/songs/canton_benbient.mp3",
                              "src/main/resources/public/songs/peppy_y=mx+b.mp3",
                              "src/main/resources/public/songs/seedai_2d.mp3",
                              "src/main/resources/public/songs/steve_counting-sheep.mp3"};

        for (String path : songPaths) {
            songMetaData = fileHandlerService.readMetaData(path);
            Beat b = beatService.getBeatFromMetadata(songMetaData);
            b.setPriceInCents(500);
            user.addBeat(b);
        }

        userService.saveUser(user);
    }
}
