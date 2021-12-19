package it.proconsole.library.video.adapter.jpa.repository.crud;

import it.proconsole.library.video.adapter.jpa.model.FilmReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmReviewCrudRepository extends JpaRepository<FilmReviewEntity, Long> {
  List<FilmReviewEntity> findByFilmId(Long filmId);
}
