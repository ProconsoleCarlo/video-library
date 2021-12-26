package it.proconsole.library.video.adapter.xlsx.model;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record FilmReviewRow(
        Long id,
        LocalDateTime date,
        Integer rating,
        @Nullable String detail
) {
}
