package dev.polar.reader.repository;

import dev.polar.reader.model.Manga;
// JpaRepository is a pre-built "Super Tool" created by the Spring team.
// It already knows how to do everything standard with a database e.g: save() = (Create/Update). findAll() = (Read all).
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// This part is called Generics. It tells the Super Tool what it is handling
// Manga: "This tool manages the Manga table."
// Long: "The ID of the Manga is a Long (number)."import org.springframework.stereotype.Repository;
@Repository // Tells Spring: "This is a Pantry Manager."
public interface MangaRepository extends JpaRepository<Manga, Long> {

    // MAGIC METHODS (Query Methods)
    // Spring sees "findByTitle" and automatically writes the SQL:
    // SELECT * FROM manga WHERE title = ?
    List<Manga> findByTitle(String title);

    // Find by part of the title (Search bar logic!)
    // SQL: SELECT * FROM manga WHERE title LIKE %keyword%
    List<Manga> findByTitleContainingIgnoreCase(String keyword);

    // Find all Mangas of a specific type (e.g., MANHWA)
    // SQL: SELECT * FROM manga WHERE type = ?
    List<Manga> findByType(String type); // Note: You might need to use the Enum type here if it fails
}