package com.cybersoft.beatstreet.service;

import com.cybersoft.beatstreet.model.Beat;
import com.cybersoft.beatstreet.repository.BeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class BeatService {

    @Autowired
    private BeatRepository beatRepository;


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

    // GETTERS AND SETTERS

    public BeatRepository getBeatRepository() {
        return beatRepository;
    }

    public void setBeatRepository(BeatRepository beatRepository) {
        this.beatRepository = beatRepository;
    }
}
