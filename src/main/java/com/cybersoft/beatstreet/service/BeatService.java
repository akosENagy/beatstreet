package com.cybersoft.beatstreet.service;

import com.cybersoft.beatstreet.model.Beat;
import com.cybersoft.beatstreet.model.User;
import com.cybersoft.beatstreet.repository.BeatRepository;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @Autowired
    private FileHandlerService fileHandlerService;

    private final String UPLOAD_FOLDER = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/public/songs/";

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
        User owner = userService.findByUsername(metadata.get("artist"));
        return new Beat(path, length, title, genre, owner);
    }

    public void uploadSong(Beat beat, MultipartFile songfile) throws IOException, InvalidDataException, UnsupportedTagException {
        byte[] bytes = songfile.getBytes();
        File toStore = new File(UPLOAD_FOLDER + songfile.getOriginalFilename());
        Files.write(toStore.toPath(), bytes);

        beat.setLengthInSeconds(fileHandlerService.getLengthOfFile(toStore.toPath().toString()));
        beat.setPath(UPLOAD_FOLDER.substring(43) + toStore.getName());

        userService.saveUser(beat.getOwner());
    }

    // GETTERS AND SETTERS

    public BeatRepository getBeatRepository() {
        return beatRepository;
    }

    public void setBeatRepository(BeatRepository beatRepository) {
        this.beatRepository = beatRepository;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public FileHandlerService getFileHandlerService() {
        return fileHandlerService;
    }

    public void setFileHandlerService(FileHandlerService fileHandlerService) {
        this.fileHandlerService = fileHandlerService;
    }
}
