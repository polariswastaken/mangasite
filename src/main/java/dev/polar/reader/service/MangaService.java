package dev.polar.reader.service;
import dev.polar.reader.model.Manga;
import dev.polar.reader.repository.MangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MangaService {

    // Dependency Injection:
    @Autowired
    private MangaRepository mangaRepository;

    // Get All Manga
    public List<Manga> getAllManga() {
        return mangaRepository.findAll();
    }

    public Manga getMangaById(long id) {
        return mangaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manga not found"));
    }

    // Add a Manga
    public Manga addManga(Manga manga) {
        // Validation Logic. Don't allow empty titles
        if (manga.getTitle() == null || manga.getTitle().isEmpty()) {
            throw new RuntimeException("Manga must have a title!");
        }
        return mangaRepository.save(manga);
    }

    // Search
    public List<Manga> searchManga(String keyword) {
        return mangaRepository.findByTitleContainingIgnoreCase(keyword);
    }
}