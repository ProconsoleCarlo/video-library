package it.proconsole.library.video.adapter.jpa.repository;

import it.proconsole.library.video.adapter.jpa.model.FilmReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmReviewRepository extends JpaRepository<FilmReview, Integer> {
}
