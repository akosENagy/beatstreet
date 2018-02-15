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

        Beat sampleBeat = new Beat("nopathyet",240, "Simplesample", 500, "Sample", user);
        Beat sampleBeat2 = new Beat("stillnopath", 230, "Even simpler sample", 350, "Sample", user);
        Beat sampleBeat3 = new Beat("wtfispath", 300, "Just one more", 500, "Sample", user);
        Beat sampleBeat4 = new Beat("wtfispath", 300, "Samplity samp", 500, "Sample", user);
        Beat sampleBeat5 = new Beat("wtfispath", 300, "Bush did 911", 500, "Sample", user);
        Beat sampleBeat6 = new Beat("wtfispath", 300, "smoke wheat erday", 500, "Sample", user);
        Beat sampleBeat7 = new Beat("wtfispath", 300, "idunno anymore", 500, "Sample", user);

        user.addBeat(sampleBeat);
        user.addBeat(sampleBeat2);
        user.addBeat(sampleBeat3);
        user.addBeat(sampleBeat4);
        user.addBeat(sampleBeat5);
        user.addBeat(sampleBeat6);
        user.addBeat(sampleBeat7);

        userService.saveUser(user);
    }
}
