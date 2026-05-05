package dev.polar.reader.service;

import dev.polar.reader.dto.ChapterRequest;
import dev.polar.reader.model.Chapter;
import dev.polar.reader.model.Manga;
import dev.polar.reader.repository.ChapterRepository;
import dev.polar.reader.repository.MangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChapterService {

    @Autowired
    private ChapterRepository chapterRepository; // DI'd to save Chapters

    @Autowired
    private MangaRepository mangaRepository; // DI'd to find Mangas

    // Takes the "Ticket" (Request) and returns a real "Chapter"
    public Chapter addChapter(ChapterRequest request) {

        // Find the Parent Manga
        Manga manga = mangaRepository.findById(request.mangaId())
                .orElseThrow(() -> new RuntimeException("Manga not found!"));

        // Create the blank Chapter
        Chapter chapter = new Chapter();

        // Fill in the details from the Request
        chapter.setChapterNumber(request.chapterNumber());
        chapter.setTitle(request.title());
        chapter.setScanlationGroup(request.scanlationGroup());
        chapter.setImageUrls(request.imageUrls());
        chapter.setUploadDate(LocalDateTime.now());

        // Connecting them
        chapter.setManga(manga);


        // Save it to the database
        return chapterRepository.save(chapter);
    }

    // Get a chapter by its ID
    public Chapter getChapter(Long id) {
        return chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
    }

    public Chapter getChapterByMangaIdAndChapterNumber(Long mangaId, float chapterNumber) {
        List<Chapter> chapters = chapterRepository.findChaptersByMangaId(mangaId);
        for (Chapter chapter : chapters) {
            if (chapter.getChapterNumber() == chapterNumber) {
                return chapter;
            }
        }
        return null;
    }
}