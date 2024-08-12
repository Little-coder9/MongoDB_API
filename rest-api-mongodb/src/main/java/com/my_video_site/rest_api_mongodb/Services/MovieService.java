package com.my_video_site.rest_api_mongodb.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my_video_site.rest_api_mongodb.Models.Movie;
import com.my_video_site.rest_api_mongodb.Repository.MovieRepository;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(String id) {
        return movieRepository.findById(id).orElse(null);
    }

    public List<Movie> searchMoviesByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Movie> searchFeaturedMovies(boolean featured) {
        return movieRepository.findByFeatured(featured);
    }

    public boolean deleteMovieById(String id) {
        movieRepository.deleteMoviesById(id);
        return true;
    }

    public Movie insertIntoMovies(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(String id, Movie movie) {
        Movie existingMovie = movieRepository.findById(id).orElse(null);

        if (existingMovie != null) {
            // Update fields if they are present in the movie
            if (movie.getTitle() != null) existingMovie.setTitle(movie.getTitle());
            if (movie.getYear() != null) existingMovie.setYear(movie.getYear());
            if (movie.getRated() != null) existingMovie.setRated(movie.getRated());
            if (movie.getReleased() != null) existingMovie.setReleased(movie.getReleased());
            if (movie.getRuntime() != null) existingMovie.setRuntime(movie.getRuntime());
            if (movie.getGenre() != null) existingMovie.setGenre(movie.getGenre());
            if (movie.getDirector() != null) existingMovie.setDirector(movie.getDirector());
            if (movie.getWriter() != null) existingMovie.setWriter(movie.getWriter());
            if (movie.getActors() != null) existingMovie.setActors(movie.getActors());
            if (movie.getPlot() != null) existingMovie.setPlot(movie.getPlot());
            if (movie.getLanguage() != null) existingMovie.setLanguage(movie.getLanguage());
            if (movie.getCountry() != null) existingMovie.setCountry(movie.getCountry());
            if (movie.getPoster() != null) existingMovie.setPoster(movie.getPoster());
            if (movie.getImdb() != null) existingMovie.setImdb(movie.getImdb());
            if (movie.getType() != null) existingMovie.setType(movie.getType());
            if (movie.getImages() != null) existingMovie.setImages(movie.getImages());
            if (movie.getFeatured() != null) existingMovie.setFeatured(movie.getFeatured());
            if (movie.getRentPrice() != 0.0) existingMovie.setRentPrice(movie.getRentPrice());
            if (movie.getPurchasePrice() != 0.0) existingMovie.setPurchasePrice(movie.getPurchasePrice());

            return movieRepository.save(existingMovie); // Save the updated movie
        }

        return null;
    }
}