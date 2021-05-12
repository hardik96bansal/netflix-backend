package com.huex.netflixbacked.utilities;

import com.huex.netflixbacked.controllers.MovieDetailsController;
import com.huex.netflixbacked.models.Genres;
import com.huex.netflixbacked.models.MovieDetails;
import com.huex.netflixbacked.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CsvFileProcessor {

    private static final Logger logger = LoggerFactory.getLogger(CsvFileProcessor.class);

    public static List<MovieDetails> importMovies(String fileName) throws IOException {
        //show_id	type	title	director	cast	country	date_added	release_year	rating	duration	listed_in	description
        List<MovieDetails> movieDetailsList = new ArrayList<>();
        String row;
        //String filePath = new File("").getAbsolutePath()+"\\src\\main\\resources\\static\\"+fileName;
        String filePath = fileName;
        System.out.println(filePath);


        BufferedReader csvReader = new BufferedReader(new FileReader(filePath));

        boolean firstRowRead = false;
        while ((row = csvReader.readLine()) != null) {
            if(!firstRowRead) {
                firstRowRead = true;
                continue;
            }
            String[] columns = row.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

            List<Person> castMembers = null;
            if(columns[4].length()>1 && columns[4].charAt(0)=='\"' && columns[4].charAt(columns[4].length()-1)=='\"')
                castMembers = Arrays.stream(columns[4].substring(1,columns[4].length()-1).split(","))
                    .map(String::trim)
                    .map(Person::new)
                    .collect(Collectors.toList());

            List<Genres> genres = null;
            if(columns[10].length()>1  && columns[10].charAt(0)=='\"' && columns[10].charAt(columns[10].length()-1)=='\"')
                genres = Arrays.stream(columns[10].substring(1,columns[10].length()-1).split(","))
                    .map(String::trim)
                    .map(Genres::new)
                    .collect(Collectors.toList());

            try{
                MovieDetails movieDetails = new MovieDetails(columns[0],columns[1],columns[2],columns[3],castMembers,columns[5],new SimpleDateFormat("dd-MMM-yy").parse(columns[6]),Integer.parseInt(columns[7]),columns[8],columns[9],Integer.parseInt(columns[9].split(" ")[0]), genres,columns[11]);
                movieDetailsList.add(movieDetails);
            }
            catch (NumberFormatException e){
                logger.warn("Invalid number provided for movie: " + columns[2] + ". Hence the current Date is considered.",e);
            }
            catch (ParseException e){
                try {
                    Date dateAdded = new SimpleDateFormat("\" MMM dd, yyyy\"").parse(columns[6]);//" January 28, 2018");//
                    MovieDetails movieDetails = new MovieDetails(columns[0],columns[1],columns[2],columns[3],castMembers,columns[5],dateAdded,Integer.parseInt(columns[7]),columns[8],columns[9],Integer.parseInt(columns[9].split(" ")[0]), genres,columns[11]);
                    movieDetailsList.add(movieDetails);
                } catch (ParseException parseException) {
                    logger.warn("Invalid date-added provided for movie: " + columns[2] + ". Hence the current Date is considered.",parseException);
                    MovieDetails movieDetails = new MovieDetails(columns[0],columns[1],columns[2],columns[3],castMembers,columns[5],new Date(),Integer.parseInt(columns[7]),columns[8],columns[9],Integer.parseInt(columns[9].split(" ")[0]), genres,columns[11]);
                    movieDetailsList.add(movieDetails);
                }
            }
        }
        logger.info("Import successful! "+movieDetailsList.size() + " titles have been imported");
        return movieDetailsList;
    }
}

