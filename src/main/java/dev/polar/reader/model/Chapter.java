package dev.polar.reader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float chapterNumber;

    private String title;

    private String scanlationGroup; // "Asura Scans", "Reaper", etc.

    private LocalDateTime uploadDate;

    // The Aggregator Logic:
    // We need to store the URLs of the images for this chapter.
    // @ElementCollection creates a separate simple table just for these URLs.
    @ElementCollection
    private java.util.List<String> imageUrls;

    // The Relationship:
    // Many Chapters belong to One Manga.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manga_id") // This creates the foreign key column
    @JsonIgnoreProperties("chapters") // Technically don't need this anymore since we got DTO's
    private Manga manga;
}