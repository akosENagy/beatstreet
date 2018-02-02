package com.cybersoft.beatstreet.controller;


import com.cybersoft.beatstreet.service.BeatService;
import com.cybersoft.beatstreet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class AjaxController {
    @Autowired
    private UserService userService;

    @Autowired
    private BeatService beatService;


    @GetMapping(value = "/api/genres")
    public Set<String> getGenres() {
        return beatService.getGenres();
    }
}
