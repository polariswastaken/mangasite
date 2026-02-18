package dev.polar.reader.service;
import dev.polar.reader.model.Manga;
import dev.polar.reader.repository.MangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Tells Spring: "This is where the logic lives."
public class MangaService {

    // Dependency Injection:
    // We need the Repository to do our job.
    // @Autowired tells Spring: "Please give me a working instance of MangaRepository."
    @Autowired
    private MangaRepository mangaRepository;

    // Feature 1: Get All Manga
    public List<Manga> getAllManga() {
        return mangaRepository.findAll();
    }

    // Feature 2: Add a Manga
    public Manga addManga(Manga manga) {
        // Validation Logic could go here (e.g., "Don't allow empty titles")
        if (manga.getTitle() == null || manga.getTitle().isEmpty()) {
            throw new RuntimeException("Manga must have a title!");
        }
        return mangaRepository.save(manga);
    }

    // Feature 3: Search
    public List<Manga> searchManga(String keyword) {
        return mangaRepository.findByTitleContainingIgnoreCase(keyword);
    }
}