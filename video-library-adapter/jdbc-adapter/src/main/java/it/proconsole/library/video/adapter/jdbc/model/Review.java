package it.proconsole.library.video.adapter.jdbc.model;

import java.time.LocalDateTime;

public record Review(
        long id,
        LocalDateTime date,
        int rating,
        String detail
) {
}
