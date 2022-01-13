package it.proconsole.library.video.adapter.xlsx.model;

import org.springframework.lang.Nullable;

import java.util.List;

public record FilmRow(
        @Nullable Long id,
        String title,
        Integer year,
        List<String> genres,
        List<FilmReviewRow> reviews
) {

}
