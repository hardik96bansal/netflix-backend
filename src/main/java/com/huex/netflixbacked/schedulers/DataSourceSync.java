package com.huex.netflixbacked.schedulers;

import com.huex.netflixbacked.services.MovieDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DataSourceSync {

    @Autowired
    MovieDetailsServiceImpl movieDetailsServiceImpl;

    @Scheduled(fixedDelay = 30000)
    public void syncDataSources() {
        System.out.println("DataSourceSync called at: " + LocalDateTime.now() + " DBQueue Size: " + movieDetailsServiceImpl.getMoviesDbQueue().size()
            + " CSVQueue Size: " + movieDetailsServiceImpl.getMoviesCsvQueue().size());

        movieDetailsServiceImpl.pushCsvQueueToCsv();
        movieDetailsServiceImpl.pushDbQueueToDb();

        System.out.println("DataSourceSync after: " + " DBQueue Size: " + movieDetailsServiceImpl.getMoviesDbQueue().size()
                + " CSVQueue Size: " + movieDetailsServiceImpl.getMoviesCsvQueue().size());
    }
}
