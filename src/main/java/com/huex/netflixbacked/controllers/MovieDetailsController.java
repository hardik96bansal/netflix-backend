package com.huex.netflixbacked.controllers;

import com.huex.netflixbacked.dto.request.MovieDetailsRequest;
import com.huex.netflixbacked.dto.response.MovieDetailsResponseDto;
import com.huex.netflixbacked.services.MovieDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MovieDetailsController {

    @Autowired
    MovieDetailsServiceImpl movieDetailsServiceImpl;

    @GetMapping("/{type}/{n}")
    public List<MovieDetailsResponseDto> getFirstNTitles(@PathVariable("type") String type, @PathVariable("n") int count){
        return movieDetailsServiceImpl.getFirstNTitles(type, count).stream().map(MovieDetailsResponseDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{type}/type/{movieType}")
    public List<MovieDetailsResponseDto> getTitlesByGenreType(@PathVariable("type") String type, @PathVariable("movieType") String genreType){
        return movieDetailsServiceImpl.getTitlesByGenreType(type, genreType).stream().map(MovieDetailsResponseDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{type}/country/{country}")
    public List<MovieDetailsResponseDto> getTitlesByCountry(@PathVariable("type") String type, @PathVariable("country") String country){
        return movieDetailsServiceImpl.getTitlesByCountry(type, country).stream().map(MovieDetailsResponseDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{type}/startDate/{startDate}/endDate/{endDate}")
    public List<MovieDetailsResponseDto> getTitlesBetweenDates(@PathVariable("type") String type, @PathVariable("startDate") String startDate,
                                                               @PathVariable("endDate") String endDate){
        return movieDetailsServiceImpl.getTitlesBetweenDates(type, startDate, endDate).stream().map(MovieDetailsResponseDto::new).collect(Collectors.toList());
    }


    @PostMapping("/title/{dataSource}")
    public void addMovieDetailsData(@PathVariable("dataSource") String dataSource, @RequestBody MovieDetailsRequest movieDetailsRequest){
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
