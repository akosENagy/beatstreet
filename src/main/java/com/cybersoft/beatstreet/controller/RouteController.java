package com.cybersoft.beatstreet.controller;

import com.cybersoft.beatstreet.service.BeatService;
import com.cybersoft.beatstreet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RouteController {

    @Autowired
    private UserService userService;

    @Autowired
    private BeatService beatService;

    @GetMapping(value = "/")
    public String renderRoot() {
        return "index";
    }



    // GETTERS AND SETTERS

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public BeatService getBeatService() {
        return beatService;
    }

    public void setBeatService(BeatService beatService) {
        this.beatService = beatService;
    }
}
