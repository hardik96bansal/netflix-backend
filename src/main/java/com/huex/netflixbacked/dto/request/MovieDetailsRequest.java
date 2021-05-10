package com.huex.netflixbacked.dto.request;

import com.huex.netflixbacked.models.Genres;
import com.huex.netflixbacked.models.MovieDetails;
import com.huex.netflixbacked.models.Person;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MovieDetailsRequest implements Serializable {
    private String showId;
    private String type;
    private String title;
    private String director;
    private String cast;
    private String country;
    private String dateAdded;
    private String releaseYear;
    private String rating;
    private String duration;
    private String genres;
    private String description;

    public MovieDetailsRequest(String showId, String type, String title, String director, String cast, String country, String dateAdded, String releaseYear, String rating, String duration, String genres, String description) {
        this.showId = showId;
        this.type = type;
        this.title = title;
        this.director = director;
        this.cast = cast;
        this.country = country;
        this.dateAdded = dateAdded;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.duration = duration;
        this.genres = genres;
        this.description = description;
    }

    public MovieDetails build(){
        List<Person> castMembers = null;
        if(cast.length()>1 && cast.charAt(0)=='\"' && cast.charAt(cast.length()-1)=='\"')
            castMembers = Arrays.stream(cast.substring(1,cast.length()-1).split(","))
                    .map(String::trim)
                    .map(Person::new)
                    .collect(Collectors.toList());

        List<Genres> genresList = null;
        if(genres.length()>1  && genres.charAt(0)=='\"' && genres.charAt(genres.length()-1)=='\"')
            genresList = Arrays.stream(genres.substring(1,genres.length()-1).split(","))
                    .map(String::trim)
                    .map(Genres::new)
                    .collect(Collectors.toList());

        MovieDetails movieDetails = null;
        try {
            movieDetails = new MovieDetails(showId, type, title, director, castMembers, country, new SimpleDateFormat("dd-MMM-yy").parse(dateAdded),Integer.parseInt(releaseYear), rating, duration, Integer.parseInt(duration.split(" ")[0]), genresList, description);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  movieDetails;
    }

    public MovieDetailsRequest() {
    }

    public String toCsvFormat() {
        return "\n" + showId +
                "," + type +
                ",'" + title +
                "," + director +
                "," + cast +
                "," + country +
                "," + dateAdded +
                "," + releaseYear +
                "," + rating +
                "," + duration +
                "," + genres +
                "," + description;
    }

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

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
