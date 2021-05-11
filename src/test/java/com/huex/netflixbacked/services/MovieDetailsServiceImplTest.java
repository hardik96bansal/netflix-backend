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

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MovieDetailsServiceImplTest {

    @Mock
    private MovieDetailsRepository movieDetailsRepository;

    @Autowired
    @InjectMocks
    private MovieDetailsServiceImpl movieDetailsServiceImpl;

    MovieDetails title1;

    @BeforeEach
    void initialize(){
        MovieDetailsRequest movieDetailsRequest = new MovieDetailsRequest();
        movieDetailsRequest.setShowId("s01");
        movieDetailsRequest.setType("Movie");
        movieDetailsRequest.setTitle("Title 01");
        movieDetailsRequest.setDirector("Director 01");
        movieDetailsRequest.setCast("Cast 01");
        movieDetailsRequest.setCountry("Country 01");
        movieDetailsRequest.setDateAdded("11-March-2011");
        movieDetailsRequest.setReleaseYear("2011");
        movieDetailsRequest.setRating("R");
        movieDetailsRequest.setDuration("103 min");
        movieDetailsRequest.setGenres("Dramas");
        movieDetailsRequest.setDescription("Description 01");

        title1 = movieDetailsRequest.build();
    }

    @Test
    void getTitlesByCountry() {
        Mockito.when(movieDetailsRepository.findById("s01")).thenReturn(Optional.ofNullable(title1));
        Assertions.assertEquals(movieDetailsServiceImpl.findById(title1.getShowId()), title1);
    }
}
