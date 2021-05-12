package com.huex.netflixbacked.utilities;

import com.huex.netflixbacked.dto.request.MovieDetailsRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFileWriter {

    public static void writeMovieDetailsToCsvFile(MovieDetailsRequest movieDetailsRequest) throws IOException {
        //String filePath = new File("").getAbsolutePath()+"\\src\\main\\resources\\static\\"+"netflix_titles.csv";
        String filePath = "netflix_titles.csv";
        FileWriter csvFileWriter = new FileWriter(filePath, true);
        csvFileWriter.append(movieDetailsRequest.toCsvFormat());
        csvFileWriter.flush();
        csvFileWriter.close();
    }
}
