package com.cybersoft.beatstreet.controller;


import com.cybersoft.beatstreet.model.Beat;
import com.cybersoft.beatstreet.model.ShoppingCart;
import com.cybersoft.beatstreet.service.BeatService;
import com.cybersoft.beatstreet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AjaxController {

    @Autowired
    private UserService userService;

    @Autowired
    private BeatService beatService;

    @Autowired
    private ShoppingCart shoppingCart;

    @GetMapping(value = "/api/genres/")
    public Set<String> getGenres() {
        return beatService.getGenres();
    }

    @GetMapping(value = "/api/genres/{genre}")
    public List<Beat> songsByGenre(@PathVariable String genre) {
        List<Beat> beats = beatService.getBeatsByGenre(genre);

        for (Beat beat : beats) {
            beat.setPath(beat.getPath().replace("src/main/resources/public", ""));
        }

        return beats;
    }

    @GetMapping("/addtocart/{beatId}")
    public ShoppingCart addToCart(@PathVariable(value="beatId") String beatId) {
        int id = Integer.valueOf(beatId);
        Beat toAdd = beatService.getBeatById(id);
        shoppingCart.addBeat(toAdd);
        return shoppingCart;
    }

    @GetMapping("/removefromcart/{beatId}")
    public ShoppingCart removeFromCart(@PathVariable(value="beatId") String beatId) {
        int id = Integer.valueOf(beatId);
        shoppingCart.removeBeatById(id);
        return shoppingCart;
    }

    @GetMapping("/getcart")
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
}
