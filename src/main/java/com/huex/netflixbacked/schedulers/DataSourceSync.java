package com.huex.netflixbacked.schedulers;

import com.huex.netflixbacked.services.MovieDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DataSourceSync {

    @Autowired
    MovieDetailsServiceImpl movieDetailsServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(DataSourceSync.class);

    @Scheduled(fixedDelay = 60000)
    public void syncDataSources() {
        logger.info("DataSourceSync called at: " + LocalDateTime.now() + " DBQueue Size: " + movieDetailsServiceImpl.getMoviesDbQueue().size()
            + " CSVQueue Size: " + movieDetailsServiceImpl.getMoviesCsvQueue().size());

        movieDetailsServiceImpl.pushCsvQueueToCsv();
        movieDetailsServiceImpl.pushDbQueueToDb();

        logger.info("DataSourceSync after: " + " DBQueue Size: " + movieDetailsServiceImpl.getMoviesDbQueue().size()
                + " CSVQueue Size: " + movieDetailsServiceImpl.getMoviesCsvQueue().size());
    }
}
