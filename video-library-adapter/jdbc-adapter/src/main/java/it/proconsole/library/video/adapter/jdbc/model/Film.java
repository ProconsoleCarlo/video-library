package it.proconsole.library.video.adapter.jdbc.model;

import java.util.List;

public record Film(long id,
                   String title,
                   int year,
                   List<Genre> genres,
                   List<FilmReview> reviews
) {
}
