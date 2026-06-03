package dev.polar.reader.repository;

import dev.polar.reader.model.Manga;
// JpaRepository is a pre-built "Super Tool" created by the Spring team.
// It already knows how to do everything standard with a database e.g: save() = (Create/Update). findAll() = (Read all).
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MangaRepository extends JpaRepository<Manga, Long> {

    Manga findByTitle(String title);
    List<Manga> findByTitleContainingIgnoreCase(String keyword);
}