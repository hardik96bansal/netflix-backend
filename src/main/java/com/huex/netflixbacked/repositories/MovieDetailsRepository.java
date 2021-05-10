package com.huex.netflixbacked.repositories;

import com.huex.netflixbacked.models.MovieDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieDetailsRepository extends CrudRepository<MovieDetails, String> {

    @Query(value = "select * from movie_details where type = ?1 order by duration limit ?2", nativeQuery = true)
    public List<MovieDetails> findAllFirstN(String type, int n);

    @Query(value = "select md.* from movie_details md inner join movie_details_genres mdg on md.show_id=mdg.movie_details_show_id where md.type = ?1 and mdg.genres_id = (select id from genres where genre= ?2);", nativeQuery = true)
    public List<MovieDetails> findByGenreType(String type, String genreType);

    @Query(value = "select * from movie_details where type=?1 and country=?2", nativeQuery = true)
    public List<MovieDetails> findByCountry(String type, String country);

    @Query(value = "select * from movie_details where type=?1 and date_added>= TO_TIMESTAMP(?2,'DD-MM-YYYY') and date_added<= TO_TIMESTAMP(?3,'DD-MM-YYYY')", nativeQuery = true)
    public List<MovieDetails> findTitleBetweenDates(String type, String startDate, String endDate);

    public Optional<MovieDetails> findById(String showId);
}
