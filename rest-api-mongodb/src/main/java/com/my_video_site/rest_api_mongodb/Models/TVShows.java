package com.my_video_site.rest_api_mongodb.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@Document(collection = "TVShows")
public class TVShows {

    @Id
    private String id;
    private String title;
    private String year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String language;
    private String country;
    private String poster;
    private String metascore;
    private String imdbRating;
    private String imdbVotes;
    private String imdbID;
    private String totalSeasons;
    private String type;
    private List<String> images;
    private Boolean featured;
    private double price;
    private double rentPrice;
    private double purchasePrice;

}
