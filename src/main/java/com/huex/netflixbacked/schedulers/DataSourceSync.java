package com.huex.netflixbacked.schedulers;

import com.huex.netflixbacked.services.MovieDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DataSourceSync {

    @Autowired
    MovieDetailsService movieDetailsService;

    @Scheduled(fixedDelay = 30000)
    public void syncDataSources() {
        System.out.println("DataSourceSync called at: " + LocalDateTime.now() + " DBQueue Size: " + movieDetailsService.getMoviesDbQueue().size()
            + " CSVQueue Size: " + movieDetailsService.getMoviesCsvQueue().size());

        movieDetailsService.pushCsvQueueToCsv();
        movieDetailsService.pushDbQueueToDb();

        System.out.println("DataSourceSync after: " + " DBQueue Size: " + movieDetailsService.getMoviesDbQueue().size()
                + " CSVQueue Size: " + movieDetailsService.getMoviesCsvQueue().size());
    }
}
