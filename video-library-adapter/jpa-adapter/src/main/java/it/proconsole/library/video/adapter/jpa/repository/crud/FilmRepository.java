package it.proconsole.library.video.adapter.jpa.repository.crud;

import it.proconsole.library.video.adapter.jpa.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Integer> {
}
