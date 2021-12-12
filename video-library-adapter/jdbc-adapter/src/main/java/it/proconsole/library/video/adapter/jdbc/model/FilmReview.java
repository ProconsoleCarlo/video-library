package it.proconsole.library.video.adapter.jdbc.model;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record FilmReview(Long id, LocalDateTime date, int rating, @Nullable String detail, Long filmId) {
}
