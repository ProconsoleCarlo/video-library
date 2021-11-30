package it.proconsole.library.video.adapter.jdbc.repository.entity;

import java.time.LocalDateTime;

public record FilmReviewEntity(long id, LocalDateTime date, int rating, String detail) {
}
