package it.proconsole.library.video.adapter.jpa.repository.crud;

import it.proconsole.library.video.adapter.jpa.model.FilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmCrudRepository extends JpaRepository<FilmEntity, Long> {
}
