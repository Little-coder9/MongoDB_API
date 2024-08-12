package com.my_video_site.rest_api_mongodb.Repository;

import com.my_video_site.rest_api_mongodb.Models.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findByFeatured(boolean featured);
    void deleteMoviesById(String id);
}
