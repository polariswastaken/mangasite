package dev.polar.reader.controller;

import dev.polar.reader.model.Manga;
import dev.polar.reader.service.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController // 1. Tells Spring: "This class handles HTTP requests (GET, POST, etc.)"
@RequestMapping("/api/manga") // 2. "All URLs starting with /api/manga come here."
public class MangaController {

    // Dependency Injection:
    // We need the Service (The Chef) to do the actual work.
    @Autowired
    private MangaService mangaService;

    // --- READ (GET) ---

    // URL: GET http://localhost:8080/api/manga
    // Returns: A list of all mangas in JSON format.
    @GetMapping
    public ResponseEntity<List<Manga>> getAllManga() {
        List<Manga> mangas = mangaService.getAllManga();
        return new ResponseEntity<>(mangas, HttpStatus.OK);
    }

    // URL: GET http://localhost:8080/api/manga/search?keyword=Solo
    // Returns: Mangas matching the keyword.
    // @RequestParam extracts "Solo" from "?keyword=Solo"
    @GetMapping("/search")
    public ResponseEntity<List<Manga>> searchManga(@RequestParam String keyword) {
        List<Manga> results = mangaService.searchManga(keyword);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    // --- CREATE (POST) ---

    // URL: POST http://localhost:8080/api/manga
    // Body: { "title": "Solo Leveling", "type": "MANHWA" ... }
    // @RequestBody: Takes the JSON sent by the user and turns it into a Java 'Manga' object.
    @PostMapping
    public ResponseEntity<Manga> addManga(@RequestBody Manga manga) {
        Manga newManga = mangaService.addManga(manga);
        // Return the created manga and a 201 CREATED status code (Standard HTTP practice)
        return new ResponseEntity<>(newManga, HttpStatus.CREATED);
    }

    // --- DELETE (DELETE) ---
    // URL: DELETE http://localhost:8080/api/manga/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManga(@PathVariable Long id) {
        // We haven't made delete logic in the service yet, but this is how the controller would look.
        // For now, let's just return OK to show the concept.
        return new ResponseEntity<>(HttpStatus.OK);
    }
}