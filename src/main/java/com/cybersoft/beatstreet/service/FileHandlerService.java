package com.cybersoft.beatstreet.service;


import com.mpatric.mp3agic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileHandlerService {

    @Autowired
    private BeatService beatService;

    @Autowired
    private UserService userService;

    public Map<String, String> readMetaData(String filePath) {
        Map<String, String> metadata = new HashMap<>();
        try {
            Mp3File file = new Mp3File(filePath);
            metadata.put("length", String.valueOf(file.getLengthInSeconds()));

            String artist, title, genre;

            if (file.hasId3v2Tag()) {
                ID3v2 id3v2 = file.getId3v2Tag();
                genre = id3v2.getGenreDescription();
                artist = id3v2.getArtist();
                title = id3v2.getTitle();
            }
            else if (file.hasId3v1Tag()) {
                ID3v1 id3v1 = file.getId3v1Tag();
                genre = id3v1.getGenreDescription();
                artist = id3v1.getArtist();
                title = id3v1.getTitle();
            }
            else {
                throw new IllegalArgumentException("File is missing IDv3 tags!");
            }

            metadata.put("artist", artist);
            metadata.put("genre", genre);
            metadata.put("title", title);
            metadata.put("path", filePath);

        } catch (IOException | UnsupportedTagException | InvalidDataException | IllegalArgumentException e) {
            // TODO, actual exception handling.
            e.printStackTrace();
        }

        return metadata;
    }

    public BeatService getBeatService() {
        return beatService;
    }

    public void setBeatService(BeatService beatService) {
        this.beatService = beatService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
