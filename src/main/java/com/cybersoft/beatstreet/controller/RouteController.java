package com.cybersoft.beatstreet.controller;

import com.cybersoft.beatstreet.model.Beat;
import com.cybersoft.beatstreet.model.User;
import com.cybersoft.beatstreet.service.BeatService;
import com.cybersoft.beatstreet.service.UserService;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
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
        model.addAttribute("beat", new Beat());
        return "songupload";
    }

    @PostMapping(value = "/upload-song")
    public String uploadSong(@ModelAttribute Beat beat,
                             @RequestParam("file") MultipartFile songfile,
                             @RequestParam("genre-other") String genre,
                             Principal principal) throws IOException, InvalidDataException, UnsupportedTagException {

        System.out.println(beat.getPriceInCents());
        User user = userService.findByUsername(principal.getName());
        beat.setOwner(user);
        user.addBeat(beat);

        System.out.println(beat.getGenre());
        if (beat.getGenre().equals("Other")) {
            beat.setGenre(genre);
        }

        beatService.uploadSong(beat, songfile);

        return "redirect:/";
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
