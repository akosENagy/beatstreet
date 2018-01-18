package com.cybersoft.beatstreet.service;

import com.cybersoft.beatstreet.repository.BeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeatService {

    @Autowired
    private BeatRepository beatRepository;


    public BeatRepository getBeatRepository() {
        return beatRepository;
    }

    public void setBeatRepository(BeatRepository beatRepository) {
        this.beatRepository = beatRepository;
    }
}
