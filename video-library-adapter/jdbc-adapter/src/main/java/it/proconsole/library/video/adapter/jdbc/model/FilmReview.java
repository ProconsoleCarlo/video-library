package it.proconsole.library.video.adapter.jdbc.model;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record FilmReview(long id, LocalDateTime date, int rating, @Nullable String detail, int filmId) {
}
