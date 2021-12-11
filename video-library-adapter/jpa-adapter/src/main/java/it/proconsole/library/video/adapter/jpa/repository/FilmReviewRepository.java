package it.proconsole.library.video.adapter.jpa.repository;

import it.proconsole.library.video.adapter.jpa.model.Film;
import it.proconsole.library.video.adapter.jpa.model.FilmReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmReviewRepository extends JpaRepository<FilmReview, Integer> {
  List<FilmReview> findByFilm(Film film);
}
