package it.proconsole.library.video.adapter.jdbc.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record FilmReview(
        long id,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime date,
        int rating,
        String detail
) {
}
