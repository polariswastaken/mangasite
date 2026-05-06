package dev.polar.reader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity // Map this class to a database table
@NoArgsConstructor // JPA needs an empty constructor
@AllArgsConstructor
@Setter
@Getter
public class Manga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID (1, 2, 3...)
    private Long id;

    @Column(nullable = false)
    private String title;
    private String alternativeTitles;
    private String author;
    private String artist;

    @Column(length = 2000)
    private String description;

    private String coverImageUrl;
    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING) // Store "MANHWA" as text in DB
    private MangaType type;

    @Enumerated(EnumType.STRING)
    private Demographic demographic;

    // relationships
    // One Manga has Many Chapters
    // "mappedBy" means the Chapter owns the relationship key
    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL, orphanRemoval = true)
    // Prevents serializing the parent Manga reference within each chapter to avoid recursive loops and reduce payload size.
    @JsonIgnoreProperties("manga")
    private List<Chapter> chapters = new ArrayList<>();
}