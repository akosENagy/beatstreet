package com.cybersoft.beatstreet.service;

import com.cybersoft.beatstreet.model.Beat;
import com.cybersoft.beatstreet.model.User;
import com.cybersoft.beatstreet.repository.BeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class BeatService {

    @Autowired
    private BeatRepository beatRepository;

    @Autowired
    private UserService userService;


    public void saveBeat(Beat beat) {
        beatRepository.save(beat);
    }

    public Beat getBeatById(int id) {
        return beatRepository.findOne(id);
    }

    public Set<String> getGenres() {
        List<Beat> beats = beatRepository.findAll();
        Set<String> genres = new HashSet<>();

        for (Beat b : beats) {
            genres.add(b.getGenre());
        }

        return genres;
    }

    public List<Beat> getBeatsByGenre(String genre) {
        List<Beat> beats = beatRepository.findAll();
        return beats
                .stream()
                .filter(beat -> beat.getGenre().equals(genre))
                .collect(Collectors.toList());
    }

    public List<Beat> getBeatsByOwner(int ownerId) {
        List<Beat> beats = beatRepository.findAll();
        return beats
                .stream()
                .filter(beat -> beat.getOwner().getId() == ownerId)
                .collect(Collectors.toList());
    }

    public List<Beat> getBeatsByOwner(User user) {
        return getBeatsByOwner(user.getId());
    }

    public Beat getBeatFromMetadata(Map<String, String> metadata) {
        //String path, int lengthInSeconds, String title, String genre, User owner
        String path = metadata.get("path");
        int length = Integer.valueOf(metadata.get("length"));
        String title = metadata.get("title");
        String genre = metadata.get("genre");
        User owner = userService.getUserByUsername(metadata.get("artist"));
        return new Beat(path, length, title, genre, owner);
    }

    // GETTERS AND SETTERS

    public BeatRepository getBeatRepository() {
        return beatRepository;
    }

    public void setBeatRepository(BeatRepository beatRepository) {
        this.beatRepository = beatRepository;
    }
}
