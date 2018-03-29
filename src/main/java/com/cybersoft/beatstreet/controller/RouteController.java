package com.cybersoft.beatstreet.controller;

import com.cybersoft.beatstreet.model.User;
import com.cybersoft.beatstreet.service.BeatService;
import com.cybersoft.beatstreet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;


@Controller
public class RouteController {

    @Autowired
    private UserService userService;

    @Autowired
    private BeatService beatService;

    @GetMapping(value = "/")
    public String renderRoot(Model model) {
        Set<String> genres = beatService.getGenres();
        model.addAttribute("genres", genres);
        return "index";
    }

    @GetMapping(value = "/register")
    public String renderRegistration(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerUser(@ModelAttribute User user) {
        userService.registerUser(user);
        return "login";
    }

    @GetMapping(value = "/login")
    public String renderLogin() { return "login"; }

    @GetMapping(value = "/upload-song")
    public String renderUploadPage(Model model) {
        model.addAttribute("genres", beatService.getGenres());
        return "songupload";
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
