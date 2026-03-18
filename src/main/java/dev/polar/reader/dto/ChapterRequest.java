package dev.polar.reader.dto;

import java.util.List;

public record ChapterRequest(
        Long mangaId,            // The ID of the parent Manga
        float chapterNumber,     // 10.5
        String title,            // "The Fight"
        String scanlationGroup,  // "Asura"
        List<String> imageUrls   // The links to the images
) {}