package dev.polar.reader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity // 1. Tells Spring: "Make a table called 'manga' in the database"
@NoArgsConstructor // 3. JPA needs an empty constructor
@AllArgsConstructor
@Setter
@Getter
public class Manga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID (1, 2, 3...)
    private Long id;

    @Column(nullable = false) // Title cannot be empty
    private String title;

    private String alternativeTitles; // "OreLev, Solo Leveling"

    private String author;
    private String artist;

    @Column(length = 2000) // Descriptions can be long!
    private String description;

    private String coverImageUrl; // We just store the link, not the image file

    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING) // Store "MANHWA" as text in DB
    private MangaType type;

    @Enumerated(EnumType.STRING)
    private Demographic demographic;

    // relationships
    // One Manga has Many Chapters
    // "mappedBy" means the Chapter owns the relationship key
    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("manga")
    private List<Chapter> chapters = new ArrayList<>();
}