package it.proconsole.videodb.database;

import it.proconsole.videodb.model.Film;
import org.springframework.data.repository.CrudRepository;

public interface FilmRepository extends CrudRepository<Film, Integer> {
}
