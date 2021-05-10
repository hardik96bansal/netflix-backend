package com.huex.netflixbacked.utilities;

import com.huex.netflixbacked.dto.request.MovieDetailsRequest;

import java.io.FileWriter;
import java.io.IOException;

public class CsvFileWriter {

    public static void writeMovieDetailsToCsvFile(MovieDetailsRequest movieDetailsRequest) throws IOException {
        FileWriter csvFileWriter = new FileWriter("");
        csvFileWriter.append(movieDetailsRequest.toCsvFormat());
        csvFileWriter.flush();
        csvFileWriter.close();
    }
}
