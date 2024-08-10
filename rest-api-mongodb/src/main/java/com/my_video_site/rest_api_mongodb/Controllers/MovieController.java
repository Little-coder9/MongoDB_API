package com.my_video_site.rest_api_mongodb.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.my_video_site.rest_api_mongodb.Models.Movie;
import com.my_video_site.rest_api_mongodb.Services.MovieService;


//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://video-streaming-site-chi.vercel.app")
@RestController
public class MovieController {


    @Autowired
    private MovieService movieService;

    @GetMapping("/Movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String id) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            return ResponseEntity.ok(movie);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/searchMovies")
    public ResponseEntity<List<Movie>> getMoviesByTitle(@RequestParam String title) {
        if (title == null || title.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Movie> movies = movieService.searchMoviesByTitle(title);
        if (movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(movies, HttpStatus.OK);

    }

    @GetMapping("/featuredMovies")
    public ResponseEntity<List<Movie>> getFeaturedMovies(@RequestParam boolean featured) {
        List<Movie> featuredMovies = movieService.searchFeaturedMovies(featured);
        if (featuredMovies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(featuredMovies, HttpStatus.OK);
    }

    @PostMapping(value = "/addMovie", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> addMovie(@RequestBody Movie movie){
        if (movie.getTitle() == null || movie.getTitle().isEmpty()) {
            return new ResponseEntity<>("Title required", HttpStatus.BAD_REQUEST);
        }

        Movie newMovie = movieService.insertIntoMovies(movie);

        return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
    }

    @PutMapping("/updateMovie/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable String id, @RequestBody Movie movie) {
        Movie movieFound = movieService.getMovieById(id);
        if (movieFound != null) {
            Movie updatedMovie = movieService.updateMovie(id, movie);
            return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
        }
        return new ResponseEntity<>("Movie not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteMovie/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable String id) {
        boolean deleted = movieService.deleteMovieById(id);
        if (deleted) {
            return new ResponseEntity<>("Movie deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Movie not found", HttpStatus.NOT_FOUND);
        }
    }
}
