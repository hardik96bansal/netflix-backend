package com.huex.netflixbacked.schedulers;

import com.huex.netflixbacked.services.MovieDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

public class DataSourceSync {

    @Autowired
    MovieDetailsService movieDetailsService;

    @Scheduled(fixedDelay = 300000)
    public void syncDataSources() {
        System.out.println("DataSourceSync called at: " + LocalDateTime.now());

        movieDetailsService.pushCsvQueueToCsv();
        movieDetailsService.pushDbQueueToDb();
    }
}
