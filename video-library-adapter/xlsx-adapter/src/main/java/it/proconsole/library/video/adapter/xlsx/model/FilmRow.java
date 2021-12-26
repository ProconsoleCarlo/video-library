package it.proconsole.library.video.adapter.xlsx.model;

import java.util.List;

public record FilmRow(
        Long id,
        String title,
        Integer year,
        String genres,
        List<FilmReviewRow> reviews
) {

}
