package com.huex.netflixbacked.services;

import com.huex.netflixbacked.dto.request.MovieDetailsRequest;
import com.huex.netflixbacked.models.MovieDetails;

import java.util.List;

public interface MovieDetailsService {

    public List<MovieDetails> getFirstNTitles(String type, String n);

    public List<MovieDetails> getTitlesByGenreType(String type, String genreType);

    public List<MovieDetails> getTitlesByCountry(String type, String country);

    public List<MovieDetails> getTitlesBetweenDates(String type, String startDate, String endDate);

    public void addMovieDetails(String dataSource, MovieDetailsRequest movieDetailsRequest);
}
