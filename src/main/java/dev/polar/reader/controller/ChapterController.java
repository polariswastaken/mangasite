package dev.polar.reader.controller;

import dev.polar.reader.dto.ChapterRequest;
import dev.polar.reader.model.Chapter;
import dev.polar.reader.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://binje.dev") // * = allow only binje.dev access to api
@RestController
@RequestMapping("/api/chapters")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @PostMapping
    public Chapter addChapter(@RequestBody ChapterRequest request) {
        return chapterService.addChapter(request);
    }

    @GetMapping("/{id}")
    public Chapter getChapter(@PathVariable Long id) {
        return chapterService.getChapter(id);
    }

    @GetMapping("/manga/{mangaId}/{chapterNumber}")
    public Chapter getChapterByMangaIdAndChapterNumber(@PathVariable Long mangaId, @PathVariable Float chapterNumber) {
        return chapterService.getChapterByMangaIdAndChapterNumber(mangaId, chapterNumber);
    }
}