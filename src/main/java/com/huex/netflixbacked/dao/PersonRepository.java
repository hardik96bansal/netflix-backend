package com.huex.netflixbacked.dao;

import com.huex.netflixbacked.models.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    public Person findById(long id);

    public Person findByName(String name);
}
