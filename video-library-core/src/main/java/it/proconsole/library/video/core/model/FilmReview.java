package it.proconsole.library.video.core.model;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record FilmReview(@Nullable Long id, LocalDateTime date, int rating, @Nullable String detail, Long filmId) {
}
