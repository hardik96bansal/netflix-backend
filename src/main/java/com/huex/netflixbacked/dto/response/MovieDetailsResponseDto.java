package com.huex.netflixbacked.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huex.netflixbacked.models.Genres;
import com.huex.netflixbacked.models.MovieDetails;
import com.huex.netflixbacked.models.Person;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MovieDetailsResponseDto implements Serializable {

    private String showId;
    private String type;
    private String title;
    private String director;
    private List<String> cast;
    private String country;
    private Date dateAdded;
    private int releaseYear;
    private String rating;
    private String durationString;
    private int duration;
    private List<String> genres;
    private String description;

    public MovieDetailsResponseDto(MovieDetails movieDetails){
        this.showId = movieDetails.getShowId();
        this.type = movieDetails.getType();
        this.title = movieDetails.getTitle();
        this.director = movieDetails.getDirector();
        this.cast = movieDetails.getCast().stream().map(Person::getName).collect(Collectors.toList());
        this.country = movieDetails.getCountry();
        this.dateAdded = movieDetails.getDateAdded();
        this.releaseYear = movieDetails.getReleaseYear();
        this.rating = movieDetails.getRating();
        this.durationString = movieDetails.getDurationString();
        this.duration = movieDetails.getDuration();
        this.genres = movieDetails.getListedIn().stream().map(Genres::getGenre).collect(Collectors.toList());
        this.description = movieDetails.getDescription();
    }

    public  MovieDetailsResponseDto(){}

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDurationString() {
        return durationString;
    }

    public void setDurationString(String durationString) {
        this.durationString = durationString;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
