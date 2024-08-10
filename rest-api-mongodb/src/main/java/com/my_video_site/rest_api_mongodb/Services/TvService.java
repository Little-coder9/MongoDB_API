package com.my_video_site.rest_api_mongodb.Services;


import com.my_video_site.rest_api_mongodb.Models.TVShows;
import com.my_video_site.rest_api_mongodb.Repository.TvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TvService {

    private final TvRepository tvRepository;

    @Autowired
    public TvService(TvRepository tvRepository) {
        this.tvRepository = tvRepository;
    }

    public List<TVShows> getAllTVShows() {
        return tvRepository.findAll();
    }

    public TVShows getTVShowById(String id) {
        return tvRepository.findById(id).orElse(null);
    }

    public List<TVShows> searchTVShowsByTitle(String title) {
        return tvRepository.findByTitleContaining(title);
    }

    public List<TVShows> searchFeaturedTVShows(boolean featured) {
        return tvRepository.findByFeatured(featured);
    }

    public boolean deleteTVShowById(String id) {
        tvRepository.deleteTVShowById(id);
        return true;
    }

    public TVShows insertIntoTVShows(TVShows TVShows) {
        return tvRepository.save(TVShows);
    }

    public TVShows updateTVShow(String id, TVShows TVShows) {
        TVShows existingTVShow = tvRepository.findById(id).orElse(null);

        if (existingTVShow != null) {
            // Update fields if they are present in the TVShows
            if (TVShows.getTitle() != null) existingTVShow.setTitle(TVShows.getTitle());
            if (TVShows.getYear() != null) existingTVShow.setYear(TVShows.getYear());
            if (TVShows.getRated() != null) existingTVShow.setRated(TVShows.getRated());
            if (TVShows.getReleased() != null) existingTVShow.setReleased(TVShows.getReleased());
            if (TVShows.getRuntime() != null) existingTVShow.setRuntime(TVShows.getRuntime());
            if (TVShows.getGenre() != null) existingTVShow.setGenre(TVShows.getGenre());
            if (TVShows.getDirector() != null) existingTVShow.setDirector(TVShows.getDirector());
            if (TVShows.getWriter() != null) existingTVShow.setWriter(TVShows.getWriter());
            if (TVShows.getActors() != null) existingTVShow.setActors(TVShows.getActors());
            if (TVShows.getPlot() != null) existingTVShow.setPlot(TVShows.getPlot());
            if (TVShows.getLanguage() != null) existingTVShow.setLanguage(TVShows.getLanguage());
            if (TVShows.getCountry() != null) existingTVShow.setCountry(TVShows.getCountry());
            if (TVShows.getPoster() != null) existingTVShow.setPoster(TVShows.getPoster());
            if (TVShows.getImdbRating() != null) existingTVShow.setImdbRating(TVShows.getImdbRating());
            if (TVShows.getType() != null) existingTVShow.setType(TVShows.getType());
            if (TVShows.getImages() != null) existingTVShow.setImages(TVShows.getImages());
            if (TVShows.getFeatured() != null) existingTVShow.setFeatured(TVShows.getFeatured());
            if (TVShows.getRentPrice() != 0.0) existingTVShow.setRentPrice(TVShows.getRentPrice());
            if (TVShows.getPurchasePrice() != 0.0) existingTVShow.setPurchasePrice(TVShows.getPurchasePrice());

            return tvRepository.save(existingTVShow); // Save the updated TVShows
        }

        return null;
    }
    
}
