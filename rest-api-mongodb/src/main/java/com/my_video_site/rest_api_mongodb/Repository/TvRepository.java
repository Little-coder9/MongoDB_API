package com.my_video_site.rest_api_mongodb.Repository;

import com.my_video_site.rest_api_mongodb.Models.TVShows;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TvRepository extends MongoRepository<TVShows, String> {

    List<TVShows> findByTitleContainingIgnoreCase(String title);
    List<TVShows> findByFeatured(boolean featured);
    void deleteTVShowById(String id);

}
