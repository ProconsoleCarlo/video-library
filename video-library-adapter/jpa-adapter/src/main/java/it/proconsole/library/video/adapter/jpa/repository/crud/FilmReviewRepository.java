package it.proconsole.library.video.adapter.jpa.repository.crud;

import it.proconsole.library.video.adapter.jpa.model.FilmReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmReviewRepository extends JpaRepository<FilmReviewEntity, Integer> {
  List<FilmReviewEntity> findByFilmId(Long filmId);
}
