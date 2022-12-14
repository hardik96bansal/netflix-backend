package com.huex.netflixbacked.services;

import com.huex.netflixbacked.dto.request.MovieDetailsRequest;
import com.huex.netflixbacked.exceptions.InternalServerException;
import com.huex.netflixbacked.exceptions.InvalidReqestDataException;
import com.huex.netflixbacked.models.Genres;
import com.huex.netflixbacked.models.MovieDetails;
import com.huex.netflixbacked.models.Person;
import com.huex.netflixbacked.dao.GenresRepository;
import com.huex.netflixbacked.dao.MovieDetailsRepository;
import com.huex.netflixbacked.dao.PersonRepository;
import com.huex.netflixbacked.utilities.CsvFileProcessor;
import com.huex.netflixbacked.utilities.CsvFileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

class MovieDetailsComparator implements Comparator<MovieDetails> {
    @Override
    public int compare(MovieDetails o1, MovieDetails o2) {
        return o1.getDuration()-o2.getDuration();
    }
}

@Service
public class MovieDetailsServiceImpl implements MovieDetailsService {
    @Autowired
    MovieDetailsRepository movieDetailsRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    GenresRepository genresRepository;

    HashMap<String, Person> personMap = new HashMap<>();
    HashMap<String, Genres> listedInMap = new HashMap<>();
    List<MovieDetails> moviesDbQueue = new ArrayList<>();
    List<MovieDetailsRequest> moviesCsvQueue = new ArrayList<>();


    private static final Logger logger = LoggerFactory.getLogger(MovieDetailsServiceImpl.class);

    public List<MovieDetails> getFirstNTitles(String type, String count){
        int n;
        try{
            n = Integer.parseInt(count);
        }
        catch (NumberFormatException exception){
            logger.error("Invalid value provided for count", exception);
            throw new InvalidReqestDataException("Invalid value provided for count");
        }
        List<MovieDetails> dbMovieList = (List<MovieDetails>) movieDetailsRepository.findAllFirstN(correctTitleType(type),n);
        dbMovieList.addAll(moviesDbQueue.stream()
                .filter(movieDetails -> movieDetails.getType().equals(correctTitleType(type)))
                .collect(Collectors.toList()));
        dbMovieList.sort(new MovieDetailsComparator());

        logger.info("getFirstNTitles result list size for params " + type + ", " + count + ": " + dbMovieList.size());
        if(dbMovieList.size()<=n) return dbMovieList;
        return dbMovieList.subList(0,n);
    }

    public List<MovieDetails> getTitlesByGenreType(String type, String genreType){
        List<MovieDetails> dbMovieList = (List<MovieDetails>) movieDetailsRepository.findByGenreType(correctTitleType(type), genreType);
        dbMovieList.addAll(moviesDbQueue.stream()
                .filter(movieDetails -> movieDetails.getType().equals(correctTitleType(type))
                        && new HashSet<>(movieDetails.getListedIn().stream().map(Genres::getGenre).collect(Collectors.toList())).contains(genreType))
                .collect(Collectors.toList()));

        dbMovieList.sort(new MovieDetailsComparator());
        logger.info("getTitlesByGenreType result list size for params " + type + ", " + genreType + ": " + dbMovieList.size());
        return  dbMovieList;
    }

    public List<MovieDetails> getTitlesByCountry(String type, String country){
        List<MovieDetails> dbMovieList = (List<MovieDetails>) movieDetailsRepository.findByCountry(correctTitleType(type), country);
        dbMovieList.addAll(moviesDbQueue.stream()
                .filter(movieDetails -> movieDetails.getType().equals(correctTitleType(type))
                        && country.equals(movieDetails.getCountry()))
                .collect(Collectors.toList()));

        dbMovieList.sort(new MovieDetailsComparator());
        logger.info("getTitlesByCountry result list size for params " + type + ", " + country + ": " + dbMovieList.size());
        return  dbMovieList;
    }

    public List<MovieDetails> getTitlesBetweenDates(String type, String startDate, String endDate){
        Date castedStartDate=new Date(), castedEndDate=new Date();
        try {
            castedStartDate= new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
            castedEndDate = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
        } catch (ParseException e) {
            logger.error("Invalid input values provided for startDate/ endDate", e);
            throw new InvalidReqestDataException("Invalid input values provided for startDate/ endDate");
        }

        List<MovieDetails> dbMovieList = (List<MovieDetails>) movieDetailsRepository.findTitleBetweenDates(correctTitleType(type), startDate, endDate);
        Date finalCastedStartDate = castedStartDate;
        Date finalCastedEndDate = castedEndDate;
        dbMovieList.addAll(moviesDbQueue.stream()
                .filter(movieDetails -> {
                    return movieDetails.getType().equals(correctTitleType(type))
                            && movieDetails.getDateAdded().after(finalCastedStartDate)
                            && movieDetails.getDateAdded().before(finalCastedEndDate);
                })
                .collect(Collectors.toList()));

        dbMovieList.sort(new MovieDetailsComparator());
        logger.info("getTitlesBetweenDates result list size for params " + type + ", " + startDate + ", " + endDate + ": " + dbMovieList.size());
        return  dbMovieList;
    }

    public static String correctTitleType(String title){
        if(title.equals("tvShows")) return "TV Show";
        if(title.equals("movies")) return "Movie";
        return title;
    }

    public void addMovieDetails(String dataSource, MovieDetailsRequest movieDetailsRequest){
        if(dataSource.equals("csv")){
            moviesDbQueue.add(movieDetailsRequest.build());
            try {
                CsvFileWriter.writeMovieDetailsToCsvFile(movieDetailsRequest);
            } catch (IOException e) {
                logger.error("Error while inserting into CSV File", e);
                throw new InternalServerException("Error while processing the request. Please check logs for error details");
            }
        }
        else if(dataSource.equals("db")){
            moviesCsvQueue.add(movieDetailsRequest);
            movieDetailsRepository.save(movieDetailsRequest.build());
        }

        else{
            throw new InvalidReqestDataException("Invalid input values provided for dbSource");
        }
        logger.info("Data has been imported to " + dataSource);
    }

    public void pushDbQueueToDb(){
        if(moviesDbQueue.size()>0) insertMoviesListIntoDB(moviesDbQueue);
        moviesDbQueue = new ArrayList<>();
    }

    public void pushCsvQueueToCsv(){
        for(MovieDetailsRequest csvRecord : moviesCsvQueue){
            try {
                CsvFileWriter.writeMovieDetailsToCsvFile(csvRecord);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new InternalServerException("Error while processing the request. Please check logs for error details");
            }
        }

        moviesCsvQueue = new ArrayList<>();
    }

    public Optional<MovieDetails> findById(String id){
        return movieDetailsRepository.findById(id);
    }

    public void loadFirstTime(){

        try {
            List<MovieDetails> movieDetailsList = CsvFileProcessor.importMovies("netflix_titles.csv");
            insertMoviesListIntoDB(movieDetailsList);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerException("Error while processing the request. Please check logs for error details");
        }
    }

    public void insertMoviesListIntoDB(List<MovieDetails> movieDetailsList){
        int personMapHits = 0;
        int genresMapHits = 0;
        LocalDateTime t1 = LocalDateTime.now();
        for(int i=0;i<movieDetailsList.size();i++){
        //for(int i=0;i<10;i++){
            List<Person> cast = movieDetailsList.get(i).getCast();
            if(cast==null) continue;
            List<Person> updatedCast = new ArrayList<>();
            //System.out.println("Currently at row:"+i);
            for(int j=0;j<cast.size();j++){
                Person fromRepo = null;
                String queryFor = cast.get(j).getName();

                if(personMap.get(""+queryFor)!=null) {
                    fromRepo = personMap.get(queryFor);
                    personMapHits++;
                }
                else{
                    //System.out.println("bb"+cast.get(j).getName());
                    personRepository.save(cast.get(j));
                    fromRepo = personRepository.findByName(queryFor);
                    System.out.println(fromRepo);
                    personMap.put(""+queryFor, fromRepo);
                    //System.out.println("ssss personMap"+personMap.keySet().size());
                }
                updatedCast.add(fromRepo);
            }
            MovieDetails listElem = movieDetailsList.get(i);
            listElem.setCast(updatedCast);
            movieDetailsList.set(i, listElem);
        }

        for(int i=0;i<movieDetailsList.size();i++){
        //for(int i=0;i<10;i++){
            List<Genres> cast = movieDetailsList.get(i).getListedIn();
            if(cast==null) continue;
            List<Genres> updatedCast = new ArrayList<>();
            for(int j=0;j<cast.size();j++){
                Genres fromRepo = null;
                String queryFor = cast.get(j).getGenre();
                System.out.println("Searching for "+queryFor);
                if (queryFor.equals("Documentaries")){
                    System.out.println("Doc");
                }
                if(listedInMap.get(""+queryFor)!=null) {
                    fromRepo = listedInMap.get("" + queryFor);
                    genresMapHits++;
                }
                else{

                    if(fromRepo==null) genresRepository.save(cast.get(j));
                    fromRepo = genresRepository.findByGenre(queryFor);
                    listedInMap.put(""+queryFor, fromRepo);
                }

                updatedCast.add(fromRepo);
            }
            MovieDetails listElem = movieDetailsList.get(i);
            listElem.setListedIn(updatedCast);
            movieDetailsList.set(i, listElem);
        }

        movieDetailsRepository.saveAll(movieDetailsList);
        LocalDateTime t2 = LocalDateTime.now();
        logger.info("Execution Time: " + ChronoUnit.SECONDS.between(t1, t2));
    }

    public HashMap<String, Person> getPersonMap() {
        return personMap;
    }

    public void setPersonMap(HashMap<String, Person> personMap) {
        this.personMap = personMap;
    }

    public HashMap<String, Genres> getListedInMap() {
        return listedInMap;
    }

    public void setListedInMap(HashMap<String, Genres> listedInMap) {
        this.listedInMap = listedInMap;
    }

    public List<MovieDetails> getMoviesDbQueue() {
        return moviesDbQueue;
    }

    public void setMoviesDbQueue(List<MovieDetails> moviesDbQueue) {
        this.moviesDbQueue = moviesDbQueue;
    }

    public List<MovieDetailsRequest> getMoviesCsvQueue() {
        return moviesCsvQueue;
    }

    public void setMoviesCsvQueue(List<MovieDetailsRequest> moviesCsvQueue) {
        this.moviesCsvQueue = moviesCsvQueue;
    }
}


