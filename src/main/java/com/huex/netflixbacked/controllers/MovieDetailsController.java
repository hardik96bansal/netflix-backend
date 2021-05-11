package com.huex.netflixbacked.controllers;

import com.huex.netflixbacked.dto.request.MovieDetailsRequest;
import com.huex.netflixbacked.dto.response.MovieDetailsResponseDto;
import com.huex.netflixbacked.services.MovieDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MovieDetailsController {

    @Autowired
    MovieDetailsServiceImpl movieDetailsServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(MovieDetailsController.class);

    @GetMapping("/{type}/{n}")
    public ResponseEntity<List<MovieDetailsResponseDto>> getFirstNTitles(@PathVariable("type") String type, @PathVariable("n") String count){
        logger.info("GET request for getFirstNTitles with parameters: " + type + ", " + count);
        List<MovieDetailsResponseDto> movieList =  movieDetailsServiceImpl.getFirstNTitles(type, count).stream().map(MovieDetailsResponseDto::new).collect(Collectors.toList());
        return  new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping("/{type}/type/{movieType}")
    public ResponseEntity<List<MovieDetailsResponseDto>> getTitlesByGenreType(@PathVariable("type") String type, @PathVariable("movieType") String genreType){
        logger.info("GET request for getTitlesByGenreType with parameters: " + type + ", " + genreType);
        List<MovieDetailsResponseDto> movieList = movieDetailsServiceImpl.getTitlesByGenreType(type, genreType).stream().map(MovieDetailsResponseDto::new).collect(Collectors.toList());
        return  new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping("/{type}/country/{country}")
    public ResponseEntity<List<MovieDetailsResponseDto>> getTitlesByCountry(@PathVariable("type") String type, @PathVariable("country") String country){
        logger.info("GET request for getTitlesByCountry with parameters: " + type + ", " + country);
        List<MovieDetailsResponseDto> movieList = movieDetailsServiceImpl.getTitlesByCountry(type, country).stream().map(MovieDetailsResponseDto::new).collect(Collectors.toList());
        return  new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping("/{type}/startDate/{startDate}/endDate/{endDate}")
    public ResponseEntity<List<MovieDetailsResponseDto>> getTitlesBetweenDates(@PathVariable("type") String type, @PathVariable("startDate") String startDate,
                                                               @PathVariable("endDate") String endDate){
        logger.info("GET request for getTitlesBetweenDates with parameters: " + type + ", " + startDate + ", " + endDate);
        List<MovieDetailsResponseDto> movieList = movieDetailsServiceImpl.getTitlesBetweenDates(type, startDate, endDate).stream().map(MovieDetailsResponseDto::new).collect(Collectors.toList());
        return  new ResponseEntity<>(movieList, HttpStatus.OK);
    }


    @PostMapping("/title/{dataSource}")
    public void addMovieDetailsData(@PathVariable("dataSource") String dataSource, @RequestBody MovieDetailsRequest movieDetailsRequest){
        logger.info("POST request for addMovieDetailsData with parameters: " + movieDetailsRequest.toString());
        movieDetailsServiceImpl.addMovieDetails(dataSource, movieDetailsRequest);
    }

    @GetMapping("/error")
    public String errorMessage(){
        return "It seems you have provided incorrect endpoint/ data. Please ensure the data is in the following format:" +
                "\n\t1. GET /{type}/count/{number}" +
                "\n\t2. GET /{type}/type/{movieType}" +
                "\n\t3. GET /{type}/country/{country}" +
                "\n\t4. GET /{type}/startDate/{startDate}/endDate/{endDate}" +
                "\n\t5. POST /title/{dataSource}";
    }


    @GetMapping("/import")
    public String importData() {
        movieDetailsServiceImpl.loadFirstTime();
        return "Data has successfully been imported.";
    }
}
