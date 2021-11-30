package it.proconsole.library.video.adapter.jdbc.repository.entity;

public record FilmEntity(
        long id,
        String title,
        int year
) {
}
