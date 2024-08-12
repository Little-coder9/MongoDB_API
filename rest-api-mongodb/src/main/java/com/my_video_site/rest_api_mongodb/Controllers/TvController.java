package com.my_video_site.rest_api_mongodb.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.my_video_site.rest_api_mongodb.Models.TVShows;
import com.my_video_site.rest_api_mongodb.Services.TvService;


@CrossOrigin(origins = "https://video-streaming-site-chi.vercel.app")

@RestController

public class TvController {

    @Autowired
    private TvService tvService;

    @GetMapping("/TVShows")
    public ResponseEntity<List<TVShows>> getAllTvShows() {
        return ResponseEntity.ok(tvService.getAllTVShows());
    }

    @GetMapping("/tv/{id}")
    public ResponseEntity<TVShows> getTvShowsById(@PathVariable String id) {
        TVShows tv = tvService.getTVShowById(id);
        if (tv != null) {
            return ResponseEntity.ok(tv);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/searchTv")
    public ResponseEntity<List<TVShows>> getTVShowsByTitle(@RequestParam String title) {
        if (title == null || title.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<TVShows> movies = tvService.searchTVShowsByTitle(title);
        if (movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(movies, HttpStatus.OK);

    }


    @GetMapping("/featuredTv")
    public ResponseEntity<List<TVShows>> getFeaturedTVShows(@RequestParam boolean featured) {
        List<TVShows> featuredTVShows = tvService.searchFeaturedTVShows(featured);
        if (featuredTVShows.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(featuredTVShows, HttpStatus.OK);
    }

    @PostMapping(value = "/addTv", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> addTVShow(@RequestBody TVShows TVShows){
        if (TVShows.getTitle() == null || TVShows.getTitle().isEmpty()) {
            return new ResponseEntity<>("At least title required", HttpStatus.BAD_REQUEST);
        }

        TVShows newTVShow = tvService.insertIntoTVShows(TVShows);

        return new ResponseEntity<>(newTVShow, HttpStatus.CREATED);
    }

    @PutMapping("/updateTv/{id}")
    public ResponseEntity<?> updateTVShow(@PathVariable String id, @RequestBody TVShows TVShows) {
        TVShows TVShowFound = tvService.getTVShowById(id);
        if (TVShowFound != null) {
            TVShows updatedTVShow = tvService.updateTVShow(id, TVShows);
            return new ResponseEntity<>(updatedTVShow, HttpStatus.OK);
        }
        return new ResponseEntity<>("TVShows not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteTv/{id}")
    public ResponseEntity<?> deleteTVShow(@PathVariable String id) {
        boolean deleted = tvService.deleteTVShowById(id);
        if (deleted) {
            return new ResponseEntity<>("TVShows deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("TVShows not found", HttpStatus.NOT_FOUND);
        }
    }

}
