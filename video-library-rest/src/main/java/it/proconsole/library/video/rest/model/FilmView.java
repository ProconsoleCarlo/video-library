package it.proconsole.library.video.rest.model;

import org.springframework.lang.Nullable;

public record FilmView(@Nullable Long id,
                       String title,
                       int year
) {
}
