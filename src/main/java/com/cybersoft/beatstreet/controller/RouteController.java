package com.cybersoft.beatstreet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RouteController {

    @GetMapping(value = "/")
    public String renderRoot() {
        return "index";
    }
}
