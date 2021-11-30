package it.proconsole.library.video.adapter.jdbc.model;

import java.time.LocalDateTime;

public record FilmReview(long id, LocalDateTime date, int rating, String detail) {
}
