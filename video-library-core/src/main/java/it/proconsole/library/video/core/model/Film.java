package it.proconsole.library.video.core.model;

import java.util.List;

public record Film(long id,
                   String title,
                   int year,
                   List<Genre> genres
) {
}
