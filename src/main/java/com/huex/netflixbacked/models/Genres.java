package com.huex.netflixbacked.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "genres")
public class Genres implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "genre")
    private String genre;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            mappedBy = "genres")
    private List<MovieDetails> titles;

    public Genres(){}

    public Genres(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<MovieDetails> getTitles() {
        return titles;
    }

    public void setTitles(List<MovieDetails> titles) {
        this.titles = titles;
    }
}
