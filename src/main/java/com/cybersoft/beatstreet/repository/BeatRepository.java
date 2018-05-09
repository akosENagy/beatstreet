package com.cybersoft.beatstreet.repository;

import com.cybersoft.beatstreet.model.Beat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeatRepository extends JpaRepository<Beat, Integer>{

    List<Beat> findTop5ByOrderByIdDesc();
}
