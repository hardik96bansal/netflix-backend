package com.huex.netflixbacked.repositories;

import com.huex.netflixbacked.models.Genres;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenresRepository extends CrudRepository<Genres, Long> {
    public Genres findById(long id);
    public Genres findByGenre(String genre);
}
