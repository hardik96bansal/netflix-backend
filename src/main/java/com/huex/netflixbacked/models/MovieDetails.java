package com.huex.netflixbacked.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "movie_details")
public class MovieDetails implements Serializable {
    @Id
    private String showId;
    private String type;
    private String title;
    private String director;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "movie_details_artist",
            joinColumns = { @JoinColumn(name = "movie_details_show_id") },
            inverseJoinColumns = { @JoinColumn(name = "persons_id") })
    @Column(name = "artist")
    @JsonIgnore
    private List<Person> cast;

    private String country;
    private Date dateAdded;
    private int releaseYear;
    private String rating;
    private String durationString;
    private int duration;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "movie_details_genres",
            joinColumns = { @JoinColumn(name = "movie_details_show_id") },
            inverseJoinColumns = { @JoinColumn(name = "genres_id") })
    @JsonIgnore
    private List<Genres> genres;
    private String description;

    public MovieDetails(String showId, String type, String title, String director, List<Person> cast, String country, Date dateAdded, int releaseYear, String rating, String durationString, int duration, List<Genres> genres, String description) {
        this.showId = showId;
        this.type = type;
        this.title = title;
        this.director = director;
        this.cast = cast;
        this.country = country;
        this.dateAdded = dateAdded;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.durationString = durationString;
        this.duration = duration;
        this.genres = genres;
        this.description = description;
    }

    public MovieDetails(){}

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

    public List<Person> getCast() {
        return cast;
    }

    public void setCast(List<Person> cast) {
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

    public List<Genres> getListedIn() {
        return genres;
    }

    public void setListedIn(List<Genres> genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

