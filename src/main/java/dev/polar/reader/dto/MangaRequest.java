package dev.polar.reader.dto;

import dev.polar.reader.model.Demographic;
import dev.polar.reader.model.MangaType;
import jakarta.persistence.*;
import java.time.LocalDate;

public record MangaRequest(
        Long id,

        @Column(nullable = false)
        String title,

        String alternativeTitles,
        String author,
        String artist,

        @Column(length = 2000)
        String description,

        String coverImageUrl,
        LocalDate releaseDate,
        MangaType type,
        Demographic demographic
) {}