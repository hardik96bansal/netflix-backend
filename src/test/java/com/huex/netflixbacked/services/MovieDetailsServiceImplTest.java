package com.huex.netflixbacked.services;

import com.huex.netflixbacked.dto.request.MovieDetailsRequest;
import com.huex.netflixbacked.models.MovieDetails;
import com.huex.netflixbacked.dao.MovieDetailsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class MovieDetailsServiceImplTest {

    @Mock
    private MovieDetailsRepository movieDetailsRepository;

    @Autowired
    @InjectMocks
    private MovieDetailsServiceImpl movieDetailsServiceImpl;

    MovieDetails title1, title2, title3, title4;
    ArrayList<MovieDetails> movieList = new ArrayList<>();

    @BeforeEach
    void initialize(){
        MovieDetailsRequest movieDetailsRequest1 = new MovieDetailsRequest();
        movieDetailsRequest1.setShowId("s01");
        movieDetailsRequest1.setType("TV Show");
        movieDetailsRequest1.setTitle("Title 01");
        movieDetailsRequest1.setDirector("Director 01");
        movieDetailsRequest1.setCast("Cast 01");
        movieDetailsRequest1.setCountry("Country 01");
        movieDetailsRequest1.setDateAdded("11-March-2011");
        movieDetailsRequest1.setReleaseYear("2011");
        movieDetailsRequest1.setRating("R");
        movieDetailsRequest1.setDuration("102 min");
        movieDetailsRequest1.setGenres("Dramas");
        movieDetailsRequest1.setDescription("Description 01");

        MovieDetailsRequest movieDetailsRequest2 = new MovieDetailsRequest();
        movieDetailsRequest2.setShowId("s02");
        movieDetailsRequest2.setType("TV Show");
        movieDetailsRequest2.setTitle("Title 01");
        movieDetailsRequest2.setDirector("Director 01");
        movieDetailsRequest2.setCast("Cast 01");
        movieDetailsRequest2.setCountry("Country 01");
        movieDetailsRequest2.setDateAdded("11-March-2011");
        movieDetailsRequest2.setReleaseYear("2011");
        movieDetailsRequest2.setRating("R");
        movieDetailsRequest2.setDuration("101 min");
        movieDetailsRequest2.setGenres("Dramas");
        movieDetailsRequest2.setDescription("Description 01");

        MovieDetailsRequest movieDetailsRequest3 = new MovieDetailsRequest();
        movieDetailsRequest3.setShowId("s03");
        movieDetailsRequest3.setType("Movie");
        movieDetailsRequest3.setTitle("Title 01");
        movieDetailsRequest3.setDirector("Director 01");
        movieDetailsRequest3.setCast("Cast 01");
        movieDetailsRequest3.setCountry("Country 01");
        movieDetailsRequest3.setDateAdded("11-March-2011");
        movieDetailsRequest3.setReleaseYear("2011");
        movieDetailsRequest3.setRating("R");
        movieDetailsRequest3.setDuration("104 min");
        movieDetailsRequest3.setGenres("Dramas");
        movieDetailsRequest3.setDescription("Description 01");

        MovieDetailsRequest movieDetailsRequest4 = new MovieDetailsRequest();
        movieDetailsRequest4.setShowId("s04");
        movieDetailsRequest4.setType("Movie");
        movieDetailsRequest4.setTitle("Title 01");
        movieDetailsRequest4.setDirector("Director 01");
        movieDetailsRequest4.setCast("Cast 01");
        movieDetailsRequest4.setCountry("India");
        movieDetailsRequest4.setDateAdded("11-March-2011");
        movieDetailsRequest4.setReleaseYear("2011");
        movieDetailsRequest4.setRating("R");
        movieDetailsRequest4.setDuration("103 min");
        movieDetailsRequest4.setGenres("Dramas");
        movieDetailsRequest4.setDescription("Description 01");

        title1 = movieDetailsRequest1.build();
        title2 = movieDetailsRequest2.build();
        title3 = movieDetailsRequest3.build();
        title4 = movieDetailsRequest4.build();

        movieList.add(title1);
        movieList.add(title2);
        movieList.add(title3);
        movieList.add(title4);
    }

    @Test
    void getFirstNTitlesSizeCheck() {
        Mockito.when(movieDetailsRepository.findAllFirstN("TV Show", 2)).thenReturn(
                movieList.stream().filter(movieDetails -> movieDetails.getType().equals("TV Show")).collect(Collectors.toList())
        );
        Assertions.assertEquals(movieDetailsServiceImpl.getFirstNTitles("tvShows","2").size(), 2);
    }

    @Test
    void getFirstNTitlesOrderCheck() {
        Mockito.when(movieDetailsRepository.findAllFirstN("TV Show", 2)).thenReturn(
                movieList.stream().filter(movieDetails -> movieDetails.getType().equals("TV Show")).collect(Collectors.toList())
        );
        List<MovieDetails> movieListFromService = movieDetailsServiceImpl.getFirstNTitles("tvShows","2");
        Assertions.assertEquals(movieListFromService.get(0).getDuration() + " " + movieListFromService.get(1).getDuration(), "101 102");
    }

    @Test
    void getTitlesByGenreTypeSizeCheck() {
        Mockito.when(movieDetailsRepository.findByGenreType("Movie", "Dramas")).thenReturn(
                movieList.stream().filter(movieDetails -> movieDetails.getType().equals("Movie")).collect(Collectors.toList())
        );
        Assertions.assertEquals(movieDetailsServiceImpl.getTitlesByGenreType("movies","Dramas").size(), 2);
    }

    @Test
    void getTitlesByGenreTypeOrderCheck() {
        Mockito.when(movieDetailsRepository.findByGenreType("Movie", "Dramas")).thenReturn(
                movieList.stream().filter(movieDetails -> movieDetails.getType().equals("Movie")).collect(Collectors.toList())
        );
        List<MovieDetails> movieListFromService = movieDetailsServiceImpl.getTitlesByGenreType("movies","Dramas");
        Assertions.assertEquals(movieListFromService.get(0).getDuration() + " " + movieListFromService.get(1).getDuration(), "103 104");
    }

    @Test
    void getTitlesByCountrySizeCheck() {
        Mockito.when(movieDetailsRepository.findByCountry("Movie", "India")).thenReturn(
                movieList.stream().filter(movieDetails -> movieDetails.getCountry().equals("India")).collect(Collectors.toList())
        );
        Assertions.assertEquals(movieDetailsServiceImpl.getTitlesByCountry("movies","India").size(), 1);
    }

    @Test
    void getTitlesBetweenDatesSizeCheck() {
        Mockito.when(movieDetailsRepository.findTitleBetweenDates("Movie", "11-03-2010","11-03-2013")).thenReturn(
                movieList.stream().filter(movieDetails -> movieDetails.getType().equals("Movie")).collect(Collectors.toList())
        );
        Assertions.assertEquals(movieDetailsServiceImpl.getTitlesBetweenDates("movies","11-03-2010", "11-03-2013").size(), 2);
    }


    @Test
    void getTitlesByIdValid() {
        Mockito.when(movieDetailsRepository.findById("s01")).thenReturn(Optional.ofNullable(title1));
        Assertions.assertEquals(movieDetailsServiceImpl.findById(title1.getShowId()), Optional.ofNullable(title1));
    }

    @Test
    void getTitlesByIdInvalid() {
        Mockito.when(movieDetailsRepository.findById("s05")).thenReturn(null);
        Assertions.assertNull(movieDetailsServiceImpl.findById("s05"));
    }
}
