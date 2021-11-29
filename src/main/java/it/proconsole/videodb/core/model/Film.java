package it.proconsole.videodb.core.model;

import java.util.List;

public record Film(long id,
                   String title,
                   int year,
                   List<Genre> genres
) {
}
