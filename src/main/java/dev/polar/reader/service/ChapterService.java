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
    private ChapterRepository chapterRepository; // Tool to save Chapters

    @Autowired
    private MangaRepository mangaRepository;     // Tool to find Mangas

    // This method takes the "Ticket" (Request) and returns a real "Chapter"
    public Chapter addChapter(ChapterRequest request) {

        // 1. Find the Parent Manga
        // .orElseThrow() means: "If you can't find it, stop everything and crash safely."
        Manga manga = mangaRepository.findById(request.mangaId())
                .orElseThrow(() -> new RuntimeException("Manga not found!"));

        // 2. Create the blank Chapter
        Chapter chapter = new Chapter();

        // 3. Fill in the details from the Request
        chapter.setChapterNumber(request.chapterNumber());
        chapter.setTitle(request.title());
        chapter.setScanlationGroup(request.scanlationGroup());
        chapter.setImageUrls(request.imageUrls());
        chapter.setUploadDate(LocalDateTime.now());

        // 4. THE MOST IMPORTANT PART: Connecting them
        chapter.setManga(manga);

        // 5. Save it to the database
        return chapterRepository.save(chapter);
    }

    // Simple method to get a chapter by its ID
    public Chapter getChapter(Long id) {
        return chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
    }
}