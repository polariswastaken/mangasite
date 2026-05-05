package dev.polar.reader.repository;

import dev.polar.reader.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    // Find all chapters for a specific Manga ID
    // SQL: SELECT * FROM chapter WHERE manga_id = ?
    List<Chapter> findByMangaId(Long mangaId);

    List<Chapter> findChaptersByMangaId(Long mangaId);
}