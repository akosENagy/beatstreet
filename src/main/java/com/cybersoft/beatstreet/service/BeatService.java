package com.cybersoft.beatstreet.service;

import com.cybersoft.beatstreet.model.Beat;
import com.cybersoft.beatstreet.repository.BeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    // GETTERS AND SETTERS

    public BeatRepository getBeatRepository() {
        return beatRepository;
    }

    public void setBeatRepository(BeatRepository beatRepository) {
        this.beatRepository = beatRepository;
    }
}
