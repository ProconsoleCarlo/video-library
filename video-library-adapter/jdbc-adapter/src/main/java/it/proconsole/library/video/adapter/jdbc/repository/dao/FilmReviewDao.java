package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.model.FilmReviewEntity;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FilmReviewDao extends DatabaseDao<FilmReviewEntity> {
  public FilmReviewDao(DataSource dataSource) {
    super(dataSource, "film_review");
  }

  public List<FilmReviewEntity> findByFilmId(Long filmId) {
    return jdbcTemplate().query("select * from film_review where film_id = ?", rowMapper(), filmId);
  }

  public List<FilmReviewEntity> saveByFilmId(List<FilmReviewEntity> entities, Long filmId) {
    var savedReviews = saveAll(entities);
    var existentReviews = findByFilmId(filmId);
    var reviewsToRemove = existentReviews.stream().filter(it -> !savedReviews.contains(it)).toList();
    deleteAll(reviewsToRemove);
    return findByFilmId(filmId);
  }

  @Override
  RowMapper<FilmReviewEntity> rowMapper() {
    return (rs, rowNum) -> new FilmReviewEntity(
            rs.getLong("id"),
            rs.getTimestamp("date").toLocalDateTime().truncatedTo(ChronoUnit.MILLIS),
            rs.getInt("rating"),
            rs.getString("detail"),
            rs.getLong("film_id")
    );
  }
}
