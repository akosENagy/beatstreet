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

        String songDirectory = "src/main/resources/public/songs/";
        String[] songPaths = {
                "belabar_sunny9.mp3",
                "canton_benbient.mp3",
                "peppy_ymxb.mp3",
                "seedai_2d.mp3",
                "steve_counting-sheep.mp3"
        };

        for (String songPath : songPaths) {
            songMetaData = fileHandlerService.readMetaData(songDirectory + songPath);
            Beat b = beatService.getBeatFromMetadata(songMetaData);
            b.setPriceInCents(500);
            user.addBeat(b);
        }

        userService.saveUser(user);
    }
}
