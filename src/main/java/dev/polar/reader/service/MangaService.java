package dev.polar.reader.service;
import dev.polar.reader.dto.MangaRequest;
import dev.polar.reader.model.Manga;
import dev.polar.reader.repository.MangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Manga getMangaByName(String name) {
        return mangaRepository.findByTitle(name);
    }

//    // Add a Manga
//    public Manga addManga(Manga manga) {
//        // Validation Logic. Don't allow empty titles
//        if (manga.getTitle() == null || manga.getTitle().isEmpty()) {
//            throw new RuntimeException("Manga must have a title!");
//        }
//        return mangaRepository.save(manga);
//    }

    // Takes the "Ticket" (Request) and returns a real "manga"
    public Manga addManga(MangaRequest request) {

        // Create the blank manga
        Manga manga = new Manga();

        // Fill in the details from the Request
        manga.setId(request.id());
        manga.setTitle(request.title());
        manga.setAlternativeTitles(request.alternativeTitles());
        manga.setDescription(request.description());
        manga.setAuthor(request.author());
        manga.setArtist(request.artist());
        manga.setCoverImageUrl(request.coverImageUrl());
        manga.setReleaseDate(request.releaseDate());
        manga.setType(request.type());
        manga.setDemographic(request.demographic());

        // Connecting them
        manga.setChapters(manga.getChapters());

        // Save it to the database
        return mangaRepository.save(manga);
    }

    // Search
    public List<Manga> searchManga(String keyword) {
        return mangaRepository.findByTitleContainingIgnoreCase(keyword);
    }
}