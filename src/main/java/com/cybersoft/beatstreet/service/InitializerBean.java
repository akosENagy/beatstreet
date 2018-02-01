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

        Beat sampleBeat = new Beat();
        sampleBeat.setOwner(user);
        sampleBeat.setLengthInSeconds(240);
        sampleBeat.setPath("nopathyet");
        sampleBeat.setGenre("Sample");
        sampleBeat.setPriceInCents(500);
        sampleBeat.setTitle("Simplesample");

        Beat sampleBeat2 = new Beat();
        sampleBeat2.setOwner(user);
        sampleBeat2.setTitle("Even simpler sample");
        sampleBeat2.setPriceInCents(350);
        sampleBeat2.setGenre("Punk");
        sampleBeat2.setPath("stillnopath");
        sampleBeat2.setLengthInSeconds(230);

        user.addBeat(sampleBeat);
        user.addBeat(sampleBeat2);

        userService.saveUser(user);
    }
}
